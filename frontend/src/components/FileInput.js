import { Button, FormHelperText, Input } from '@material-ui/core';
import { ErrorMessage, useFormikContext } from 'formik';
import React from 'react';
import { useTranslation } from 'react-i18next';
import { themeUtils } from '../theme';
import { withStyles } from '@material-ui/core/styles';

const FileInput = ({ name }) => {
  const { t } = useTranslation();

  const { values, setFieldValue } = useFormikContext();

  return (
    <div className="h-14">
      <div className="w-full flex justify-between">
        <Input
          name={name}
          onChange={(event) =>
            event.target.files[0] !== undefined &&
            setFieldValue(name, event.target.files[0])
          }
          type="file"
          id="fileInput"
          className="w-full"
        />
        <GreyButton
          disabled={values.image === ''}
          onClick={() => {
            setFieldValue(name, '');
            document.querySelector('#fileInput').value = '';
          }}
        >
          {t('register.discardimage')}
        </GreyButton>
      </div>
      <FormHelperText>
        <ErrorMessage name={name} />
      </FormHelperText>
    </div>
  );
};

const GreyButton = withStyles({
  root: {
    color: themeUtils.colors.grey,
    backgroundColor: themeUtils.colors.lightGrey,
    transition: 'color 0.1s',
    '&:hover': {
      color: 'white',
      backgroundColor: themeUtils.colors.grey,
      transition: 'color 0.1s',
    },
    fontSize: '1em',
  },
})(Button);

export default FileInput;
