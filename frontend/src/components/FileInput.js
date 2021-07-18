import { Button, FormHelperText, Input } from '@material-ui/core';
import { ErrorMessage, useFormikContext } from 'formik';
import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { themeUtils } from '../theme';
import { withStyles } from '@material-ui/core/styles';

const MAX_SIZE = 2 * 1024 * 1024;

const VALID_TYPES = ['image/png', 'image/jpeg'];

const MAX_FILES = 5;

const FileInput = ({ fileName, multiple = false }) => {
  const { t } = useTranslation();

  const [disabled, setDisabled] = useState(true);

  const { setFieldValue } = useFormikContext();

  return (
    <div className="h-14">
      <div className="w-full flex justify-between">
        <Input
          name={fileName}
          onChange={(event) => {
            if (event.target.files[0] !== undefined) {
              setFieldValue(
                fileName,
                multiple ? event.target.files : event.target.files[0]
              );
              setDisabled(false);
            }
          }}
          type="file"
          className="w-full"
          inputProps={{ multiple: multiple }}
          id="fileInput"
        />
        <GreyButton
          disabled={disabled}
          onClick={() => {
            setFieldValue(fileName, '');
            document.querySelector('#fileInput').value = '';
            setDisabled(true);
          }}
        >
          {t('register.discardimage')}
        </GreyButton>
      </div>
      <FormHelperText>
        <ErrorMessage name={fileName} />
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

export function checkType(file) {
  if (file === undefined || file === '') return true;

  return VALID_TYPES.includes(file.type);
}

export function checkSize(file) {
  if (file === undefined || file === '') return true;

  return file.size <= MAX_SIZE;
}

export function checkQuantity(files) {
  if (files === undefined || files === '') return true;

  return Array.from(files).length <= MAX_FILES;
}

export default FileInput;
