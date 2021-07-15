import React from 'react';
import Visibility from '@material-ui/icons/Visibility';
import VisibilityOff from '@material-ui/icons/VisibilityOff';
import {
  FilledInput,
  InputLabel,
  FormControl,
  InputAdornment,
  IconButton,
} from '@material-ui/core';

const FormControlPassword = ({
  placeholder,
  variable,
  handleChange,
  value,
  fullWidth,
}) => {
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
    <FormControl fullWidth={fullWidth} variant="filled">
      <InputLabel>{placeholder}</InputLabel>
      <FilledInput
        id={variable}
        type={values.toggle ? 'text' : 'password'}
        value={value}
        onChange={handleChange}
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
    </FormControl>
  );
};

export default FormControlPassword;
