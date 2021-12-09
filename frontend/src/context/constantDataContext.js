
import React,{useEffect, useState } from 'react';
import {
  useCategories,
  useZones,
  useJobCards,
  useRateTypes,
  useContracts,
} from '../hooks';
import { createContext } from "react";

const ConstantDataContext = createContext(null);


export const ConstantDataContextProvider  = ({children}) => {
  const { getCategories } = useCategories();
  const { getZones } = useZones();
  const { getOrderByParams } = useJobCards();
  const { getRateTypes } = useRateTypes();
  const { getContractStates } = useContracts();
  const [zones, setZones] = useState([]);
  const [categories, setCategories] = useState([]);
  const [orderByParams, setOrderByParams] = useState([]);
  const [rateTypes, setRateTypes] = useState([]);
  const [contractStates, setContractStates] = useState([]);
  /*
   * This functions load the categories, zones, order-by params and rate-types from the server.
   */
  const loadCategories = async () => {
    try {
      const categories = await getCategories();
      setCategories(
        categories.sort((category, category2) =>
          category.description.localeCompare(category2.description)
        )
      );
    } catch (e) {
      // console.log(e);
    }
  };

  const loadZones = async () => {
    try {
      const zones = await getZones();
      setZones(
        zones.sort((zone1, zone2) =>
          zone1.description.localeCompare(zone2.description)
        )
      );
    } catch (e) {
      // console.log(e);
    }
  };

  const loadOrderByParams = async () => {
    try {
      const orderByParams = await getOrderByParams();
      setOrderByParams(orderByParams);
    } catch (e) {
      // console.log(e);
    }
  };

  const loadRateTypes = async () => {
    try {
      const rateTypes = await getRateTypes();
      setRateTypes(rateTypes);
    } catch (e) {
      // console.log(e);
    }
  };

  const loadContractStates = async () => {
    try {
      const contractStates = await getContractStates();
      setContractStates(contractStates);
    } catch (e) {
      // console.log(e);
    }
  };

  /*
   * This effect will be called when the page it's first loaded
   * It will check if the user is logged in and if so it will set the token and user data in the context.
   * It will also load the categories and zones data from the server.
   */
  useEffect(() => {
    loadCategories();
    loadZones();
    loadOrderByParams();
    loadRateTypes();
    loadContractStates();
    return () => {
      setContractStates([]);
      setRateTypes([]);
      setOrderByParams([]);
      setZones([]);
      setCategories([]);
    };
  },[]);


  return (
    <ConstantDataContext.Provider
        value={{
          categories: categories,
          zones: zones,
          orderByParams: orderByParams,
          rateTypes: rateTypes,
          states: contractStates,
        }}
      >
        {children}
      </ConstantDataContext.Provider>
  )
}

export default ConstantDataContext;
