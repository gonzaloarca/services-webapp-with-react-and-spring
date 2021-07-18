import {
  Checkbox,
  Chip,
  Divider,
  InputAdornment,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
  makeStyles,
  TextField,
} from '@material-ui/core';
import { LocationOn, Search } from '@material-ui/icons';
import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { themeUtils } from '../theme';

const locationList = [
  {
    description: 'La Boca',
    id: 0,
  },
  {
    description: 'San Juan',
    id: 1,
  },
  {
    description: 'San Luis',
    id: 2,
  },
  {
    description: 'Recoleta',
    id: 3,
  },
  {
    description: 'Almagro',
    id: 4,
  },
  {
    description: 'Palermo',
    id: 5,
  },
  {
    description: 'San Isidro',
    id: 6,
  },
  {
    description: 'Tigre',
    id: 7,
  },
  {
    description: 'Retiro',
    id: 8,
  },
  {
    description: 'Villa Constitucion',
    id: 9,
  },
];

const useStyles = makeStyles((theme) => ({
  container: {
    width: '70%',
    margin: '0 auto',
  },
  list: {
    backgroundColor: 'white',
    height: 390,
    overflowY: 'auto',
    boxShadow: 'inset 0 -20px 10px -10px rgba(0,0,0,0.1)',
    borderRadius: '0 0 10px 10px',
    border: '2px solid #f1f1f1',
    margin: '0 auto',
  },
  searchInput: {
    fontSize: themeUtils.fontSizes.sm,
    fontWeight: 500,
  },
  filterImage: {
    height: '40%',
    width: '40%',
    objectFit: 'contain',
    marginBottom: 10,
  },
  noResultsText: {
    textAlign: 'center',
    width: '80%',
    WebkitLineClamp: 3,
    display: '-webkit-box',
    WebkitBoxOrient: 'vertical',
    overflowWrap: 'break-word',
    overflow: 'hidden',
    textOverflow: 'ellipsis',
  },
  selections: {
    padding: 10,
    minHeight: 120,
    boxShadow: themeUtils.shadows.containerShadow,
    borderRadius: 10,
    marginBottom: 20,
  },
}));

const LocationList = ({ multiple = false }) => {
  const classes = useStyles();
  const [checked, setChecked] = useState([]);
  const [filter, setFilter] = useState('');
  const { t } = useTranslation();

  const handleToggle = (value) => () => {
    const currentIndex = checked.indexOf(value);
    const newChecked = [...checked];

    if (currentIndex === -1) {
      newChecked.push(value);
    } else {
      newChecked.splice(currentIndex, 1);
    }

    setChecked(newChecked);
  };

  const handleDelete = (id) => {
    const newChecked = [...checked].filter((v) => v !== id);
    setChecked(newChecked);
  };

  const renderList = (list) => {
    const renderedList = list
      .filter(
        ({ description }) =>
          description
            .toLowerCase()
            .startsWith(filter.trimStart().trimEnd().toLowerCase()) ||
          filter === ''
      )
      .map(({ id, description }) => {
        const labelId = `checkbox-list-label-${id}`;

        return (
          <>
            <ListItem
              key={id}
              role={undefined}
              dense
              button
              onClick={handleToggle(id)}
            >
              <ListItemIcon>
                <Checkbox
                  edge="start"
                  checked={checked.indexOf(id) !== -1}
                  tabIndex={-1}
                  disableRipple
                  inputProps={{ 'aria-labelledby': labelId }}
                />
              </ListItemIcon>
              <ListItemText id={labelId} primary={description} />
            </ListItem>
            <Divider />
          </>
        );
      });
    if (renderedList.length > 0) return renderedList;
    else
      return (
        <div className="flex flex-col justify-center items-center h-full opacity-40">
          <img
            className={classes.filterImage}
            src={process.env.PUBLIC_URL + '/img/location-search.svg'}
            alt=""
          />
          <p className={classes.noResultsText}>
            {t('nolocations', { location: filter })}
          </p>
        </div>
      );
  };

  return (
    <div className={classes.container}>
      {multiple && (
        <div className={classes.selections}>
          <h3 className="font-semibold">
            {t('createjobpost.steps.locations.selected')}
          </h3>

          {checked.length > 0 ? (
            checked.map((id) => (
              <Chip
                className="m-1"
                label={
                  locationList.filter((zone) => zone.id === id)[0].description
                }
                style={{
                  backgroundColor: themeUtils.colors.lightBlue,
                  fontWeight: 500,
                  color: 'white',
                }}
                icon={<LocationOn className="text-white" />}
                key={id}
                onDelete={() => handleDelete(id)}
              />
            ))
          ) : (
            <div className="flex justify-center items-center font-medium text-sm opacity-50 ">
              {t('createjobpost.steps.locations.notselected')}
            </div>
          )}
        </div>
      )}
      <TextField
        fullWidth
        hiddenLabel
        variant="filled"
        placeholder={t('locationfilter')}
        value={filter}
        onChange={(e) => setFilter(e.target.value)}
        InputProps={{
          startAdornment: (
            <InputAdornment position="start">
              <Search />
            </InputAdornment>
          ),
          classes: {
            input: classes.searchInput,
          },
        }}
      />
      <List className={classes.list}>{renderList(locationList)}</List>
    </div>
  );
};

export default LocationList;
