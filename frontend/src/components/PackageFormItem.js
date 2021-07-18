import { faCube } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  Divider,
  FormControl,
  FormControlLabel,
  FormHelperText,
  IconButton,
  makeStyles,
  Radio,
  RadioGroup,
  TextField,
  Tooltip,
} from '@material-ui/core';
import { Help, Close } from '@material-ui/icons';
import { ErrorMessage, useFormikContext } from 'formik';
import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { themeUtils } from '../theme';
import rateTypeI18nMapper from '../i18n/mappers/rateType';

const rateTypes = [
  {
    id: 0,
    description: 'HOURLY',
  },
  {
    id: 1,
    description: 'ONE_TIME',
  },
  {
    id: 2,
    description: 'TBD',
  },
];

const useStyles = makeStyles((theme) => ({
  packageForm: {
    position: 'relative',
    padding: 30,
    backgroundColor: themeUtils.colors.blue,
    color: 'white',
    borderRadius: 10,
    width: '100%',
    boxShadow: themeUtils.shadows.containerShadow,
  },
  packageLabel: {
    color: 'white',
    fontWeight: 600,
    marginBottom: 10,
    fontSize: themeUtils.fontSizes.base,
  },
  packageInput: {
    color: 'white',
    backgroundColor: themeUtils.colors.darkBlue,
    fontSize: themeUtils.fontSizes.sm,
    fontWeight: 500,
  },
  packagesRadioContainer: {
    backgroundColor: 'white',
    color: 'black',
    borderRadius: 10,
    boxShadow: themeUtils.shadows.containerShadow,
    width: '100%',
    display: 'flex',
    justifyContent: 'center',
    padding: 10,
  },
  packageHeader: {
    fontSize: themeUtils.fontSizes.h2,
    fontWeight: 600,
    marginLeft: 10,
  },
  packageDescription: {
    backgroundColor: themeUtils.colors.darkBlue,
    '& .MuiFilledInput-root': {
      backgroundColor: 'transparent',
    },
  },
  errorMessage: {
    color: themeUtils.colors.red,
    backgroundColor: themeUtils.colors.lightRed,
    width: '100%',
    borderRadius: '0.25rem',
    padding: '0 0.5rem 0 0.5rem',
  },
}));

const PackageFormItem = ({ index, withDelete }) => {
  const classes = useStyles();
  const { t } = useTranslation();
  const { values, setFieldValue } = useFormikContext();

  const [hidden, setHidden] = useState(false);

  const handleDelete = () => {
    setFieldValue('packages', [
      ...values.packages.filter((_, i) => i !== index),
    ]);
  };

  return (
    <div className={classes.packageForm}>
      <div className="flex items-center justify-between">
        <div className="flex items-center">
          <FontAwesomeIcon className="text-lg" icon={faCube} />
          <span className={classes.packageHeader}>
            {t('createjobpost.steps.packages.package')}
          </span>
          <Tooltip
            title={
              <div className="text-xs">
                <strong>
                  {t('createjobpost.steps.packages.help.question')}
                </strong>
                <br />
                <i>{t('createjobpost.steps.packages.help.answer')}</i>
              </div>
            }
          >
            <IconButton disableRipple>
              <Help className="text-white opacity-50" />
            </IconButton>
          </Tooltip>
        </div>
        {withDelete && (
          <IconButton onClick={() => handleDelete()}>
            <Close className="text-white" />
          </IconButton>
        )}
      </div>

      <p className="text-sm font-medium opacity-50">
        {t('createjobpost.required')}
      </p>
      <Divider className="bg-white opacity-50 mb-4" />
      <p className={classes.packageLabel}>
        {t('createjobpost.steps.packages.title.label')}
      </p>
      <div className="mb-5">
        <TextField
          fullWidth
          hiddenLabel
          size="small"
          InputProps={{
            classes: {
              input: classes.packageInput,
            },
          }}
          name={`packages[${index}].title`}
          value={values.packages[index].title}
          placeholder={t('createjobpost.steps.packages.title.placeholder')}
          variant="filled"
          onChange={(e) => {
            setFieldValue(`packages[${index}].title`, e.target.value);
          }}
        />
        <FormHelperText className={classes.errorMessage}>
          <ErrorMessage name={`packages[${index}].title`}></ErrorMessage>
        </FormHelperText>
      </div>

      <p className={classes.packageLabel}>
        {t('createjobpost.steps.packages.description.label')}
      </p>
      <div className="mb-5">
        <TextField
          fullWidth
          hiddenLabel
          multiline
          rows={3}
          size="small"
          InputProps={{
            classes: {
              input: classes.packageInput,
            },
          }}
          name={`packages[${index}].description`}
          value={values.packages[index].description}
          placeholder={t(
            'createjobpost.steps.packages.description.placeholder'
          )}
          variant="filled"
          onChange={(e) =>
            setFieldValue(`packages[${index}].description`, e.target.value)
          }
          className={classes.packageDescription}
        />
        <FormHelperText className={classes.errorMessage}>
          <ErrorMessage name={`packages[${index}].description`}></ErrorMessage>
        </FormHelperText>
      </div>

      {/* RateType */}
      <p className={classes.packageLabel}>
        {t('createjobpost.steps.packages.ratetype.label')}
      </p>

      <FormControl className="w-full mb-5" component="fieldset">
        <RadioGroup
          row
          name={`packages[${index}].rateType`}
          value={values.packages[index].rateType}
          onChange={(e) => {
            setFieldValue(`packages[${index}].rateType`, e.target.value);
            if (parseInt(e.target.value) === 2) setHidden(true);
            else setHidden(false);
          }}
          className={classes.packagesRadioContainer}
        >
          {rateTypes.map(({ id }) => (
            <FormControlLabel
              value={`${id}`}
              key={id}
              label={
                <p className="text-sm font-medium">
                  {t(rateTypeI18nMapper[id])}
                </p>
              }
              control={<Radio />}
            />
          ))}
        </RadioGroup>
        <FormHelperText className={classes.errorMessage}>
          <ErrorMessage name={`packages[${index}].rateType`}></ErrorMessage>
        </FormHelperText>
      </FormControl>

      <div hidden={hidden}>
        <p className={classes.packageLabel}>
          {t('createjobpost.steps.packages.price.label')}
        </p>
        <TextField
          fullWidth
          type="number"
          size="small"
          inputProps={{
            min: '0',
            step: 'any',
          }}
          hiddenLabel
          InputProps={{
            classes: {
              input: classes.packageInput,
            },
          }}
          name={`packages[${index}].price`}
          value={values.packages[index].price}
          placeholder={t('createjobpost.steps.packages.price.placeholder')}
          variant="filled"
          onChange={(e) =>
            setFieldValue(`packages[${index}].price`, e.target.value)
          }
        />
        <FormHelperText className={classes.errorMessage}>
          <ErrorMessage name={`packages[${index}].price`}></ErrorMessage>
        </FormHelperText>
      </div>
    </div>
  );
};

export default PackageFormItem;
