/*  Funciones para filtar los dias del day picker.
 **  Está seteado para poder elegir despues de las 24hs del dia de hoy.
 */

export const filterPastTime = (date) => {
  const currentDate = new Date();
  const selectedDate = new Date(date);

  return selectedDate.getTime() - currentDate.getTime() > 24 * 60 * 60 * 1000;
};

export const filterPastDate = (date) => {
  const currentDate = new Date();
  const selectedDate = new Date(date);

  return currentDate.getTime() < selectedDate.getTime();
};
