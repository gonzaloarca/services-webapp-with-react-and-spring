import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Visibility from '@material-ui/icons/Visibility';
import VisibilityOff from '@material-ui/icons/VisibilityOff';
import { IconButton, TextField } from '@material-ui/core';
import { Field, ErrorMessage } from 'formik';
import LoginAndRegisterStyles from '../components/LoginAndRegisterStyles';

const useStyles = makeStyles(LoginAndRegisterStyles);

const FormControlPassword = ({
  placeholder,
  variable,
  fullWidth,
  required,
  onSubmit,
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
      as={TextField}
      variant="filled"
      fullWidth={fullWidth}
      label={placeholder}
      name={variable}
      required={required}
	  onSubmit={onSubmit}
      className={classes.FieldHeight}
	  type={values.toggle ? 'text' : 'password'}
      helperText={<ErrorMessage name={variable}></ErrorMessage>}
      InputProps={{
        endAdornment: (
          <IconButton
            onClick={handleClickShowPassword}
            onMouseDown={handleMouseDownPassword}
          >
            {values.toggle ? <Visibility /> : <VisibilityOff />}
          </IconButton>
        ),
      }}
    />
  );
};

export default FormControlPassword;
