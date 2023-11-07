import {
  GET_BUILDING,
  GET_RESTAURANT,
  GET_RESTAURANT_BY_ID,
  GET_RESTAURANT_MENU,
  SET_RESTAURANT_LOADING,
  SET_SELECTED_BUILDINGS,
} from "../Types/RestaurantTypes";

export const getRestaurants = (payload) => ({
  type: GET_RESTAURANT,
  payload,
});

export const setRestaurantLoading = (payload) => ({
  type: SET_RESTAURANT_LOADING,
  payload,
});

export const getBuildings = (payload) => ({
  type: GET_BUILDING,
  payload,
});

export const setSelectedBuildingRedux = (payload) => ({
  type: SET_SELECTED_BUILDINGS,
  payload,
});

export const getRestaurantById = (payload) => ({
  type: GET_RESTAURANT_BY_ID,
  payload,
});

export const getRestaurantMenu = (payload) => ({
  type: GET_RESTAURANT_MENU,
  payload,
});
