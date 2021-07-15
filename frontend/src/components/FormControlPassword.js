import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Visibility from '@material-ui/icons/Visibility';
import VisibilityOff from '@material-ui/icons/VisibilityOff';
import {
  FilledInput,
  InputLabel,
  FormControl,
  InputAdornment,
  IconButton,
  FormHelperText,
} from '@material-ui/core';
import { Field, ErrorMessage } from 'formik';
import LoginAndRegisterStyles from '../components/LoginAndRegisterStyles';

const useStyles = makeStyles(LoginAndRegisterStyles);

const FormControlPassword = ({
  placeholder,
  variable,
  fullWidth,
  required,
}) => {
  const classes = useStyles();
  const [values, setValues] = React.useState({
    toggle: false,
  });
  const handleClickShowPassword = () => {
    setValues({ ...values, toggle: !values.toggle });
  };

  const handleMouseDownPassword = (event) => {
    event.preventDefault();
  };

  return (
    <Field
      as={FormControl}
      fullWidth={fullWidth}
      required={required}
      variant="filled"
      name={variable}
      className={classes.FieldHeight}
    >
      <InputLabel>{placeholder}</InputLabel>
      <FilledInput
        id={variable}
        type={values.toggle ? 'text' : 'password'}
        required
        endAdornment={
          <InputAdornment position="end">
            <IconButton
              onClick={handleClickShowPassword}
              onMouseDown={handleMouseDownPassword}
            >
              {values.toggle ? <Visibility /> : <VisibilityOff />}
            </IconButton>
          </InputAdornment>
        }
      />
      <FormHelperText>
        <ErrorMessage name={variable} />
      </FormHelperText>
    </Field>
  );
};

export default FormControlPassword;
