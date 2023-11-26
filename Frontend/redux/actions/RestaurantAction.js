import {
  GET_BUILDING,
  GET_RESTAURANT,
  GET_RESTAURANT_BY_ID,
  GET_RESTAURANT_MENU,
  SET_RESTAURANT_LOADING,
  SET_SELECTED_BUILDINGS,
  GET_RESTAURANT_REVIEW,
  GET_RESTAURANT_MENUS,
  SET_RESTAURANT_MENUITEM,
  UPDATE_RESTAURANT_MENUITEM,
  DELETE_RESTAURANT_MENUITEM
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

export const getAllRestaurantReviews = (payload) => ({
  type: GET_RESTAURANT_REVIEW,
  payload,
});
export const getRestaurantMenus = (payload) => ({
  type: GET_RESTAURANT_MENUS,
  payload,
});

export const addRestaurantMenuItem = (payload) => ({
  type: SET_RESTAURANT_MENUITEM,
  payload,
});

export const updateRestaurantMenuItem = (payload) => ({
  type: UPDATE_RESTAURANT_MENUITEM,
  payload,
});

export const deleteRestaurantMenuItem = (payload) => ({
  type: DELETE_RESTAURANT_MENUITEM,
  payload,
})
