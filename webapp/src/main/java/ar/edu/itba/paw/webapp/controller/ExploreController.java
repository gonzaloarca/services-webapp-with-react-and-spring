package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.JobCardService;
import ar.edu.itba.paw.interfaces.services.PaginationService;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.JobPostZone;
import ar.edu.itba.paw.webapp.form.SearchForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;

@Controller
public class ExploreController {

    private final Logger mainLogger = LoggerFactory.getLogger(ExploreController.class);

    @Autowired
    private JobCardService jobCardService;

    @Autowired
    private PaginationService paginationService;

    @Autowired
    private LocaleResolver localeResolver;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home(@ModelAttribute("searchForm") SearchForm form, @RequestParam(value = "page", required = false, defaultValue = "1") final int page) {
        if (page < 1) {
            mainLogger.error("Invalid search page");
            throw new IllegalArgumentException();
        }
        int maxPage = paginationService.findMaxPageJobCards();
        return new ModelAndView("index")
                .addObject("jobCards", jobCardService.findAll(page - 1))
                .addObject("maxPage", maxPage)
                .addObject("currentPages", paginationService.findCurrentPages(page, maxPage))
                .addObject("categories", Arrays.copyOfRange(JobPost.JobType.values(), 0, 3));
    }

    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public ModelAndView search(@Valid @ModelAttribute("searchForm") SearchForm form, final BindingResult errors,
                               final ModelAndView mav, @RequestParam(value = "page", required = false, defaultValue = "1") final int page,
                               HttpServletRequest request) {
        if (page < 1) {
            mainLogger.error("Invalid search page");
            throw new IllegalArgumentException();
        }

        int zone;
        int category = form.getCategory();
        try {
            zone = Integer.parseInt(form.getZone());
        } catch (NumberFormatException e) {
            mainLogger.error("Parse error in form zone");
            throw new IllegalArgumentException();
        }

        if (errors.hasErrors()) {
            mainLogger.debug("Search form has errors: {}", errors.getAllErrors().toString());
            return new ModelAndView(mav.getViewName()).addObject("pickedZone", JobPostZone.Zone.values()[zone])
                    .addObject("categories", JobPost.JobType.values());
        }
        String query = form.getQuery();

        final ModelAndView searchMav = new ModelAndView("search");
        searchMav
                .addObject("categories", JobPost.JobType.values())
                .addObject("pickedZone", JobPostZone.Zone.values()[zone]);
        int maxPage = paginationService.findMaxPageJobPostsSearch(form.getQuery(), zone, category);
        searchMav
                .addObject("maxPage", maxPage)
                .addObject("currentPages", paginationService.findCurrentPages(page, maxPage));

        mainLogger.debug("Searching job cards with query: {}, zone: {}, category: {} and page: {}", query, zone, category, page);

        searchMav.addObject("jobCards", jobCardService.search(query, zone, category, page - 1,
                localeResolver.resolveLocale(request)));
        return searchMav;
    }

    @RequestMapping("/categories")
    public ModelAndView categories() {
        return new ModelAndView("categories")
                .addObject("categories", JobPost.JobType.values());
    }

}
