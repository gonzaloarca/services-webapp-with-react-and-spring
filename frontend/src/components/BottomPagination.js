import { Pagination } from '@material-ui/lab';
import React from 'react';
const BottomPagination = ({ maxPage, queryParams, setQueryParams }) => {
  const [page, setPage] = React.useState(1);
  const handlePageChange = (_event, page) => {
    setPage(page);
    setQueryParams({ ...queryParams, page: page });
  };
  return (
    <div className="flex justify-center pt-6">
      <Pagination
        count={maxPage}
        defaultPage={1}
        boundaryCount={1}
        page={page}
        onChange={handlePageChange}
      />
    </div>
  );
};

export default BottomPagination;
