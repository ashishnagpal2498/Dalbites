import {
  GET_BUILDING,
  GET_RESTAURANT,
  GET_RESTAURANT_BY_ID,
  GET_RESTAURANT_MENU,
  SET_RESTAURANT_LOADING,
  SET_SELECTED_BUILDINGS,
  GET_RESTAURANT_MENUS,
  SET_RESTAURANT_MENUITEM,
  UPDATE_RESTAURANT_MENUITEM,
  DELETE_RESTAURANT_MENUITEM,
  ADD_CART_ITEM,
  UPDATE_CART_ITEM,
  GET_CART_ITEM,
  DELETE_CART_ITEM,
  DELETE_CART_ITEMS,
  SELECT_RESTAURANT_FOR_CART
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

export const getRestaurantMenus = (payload) => ({
  type: GET_RESTAURANT_MENUS,
  payload,
})

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

export const getCartItem = (payload) => ({
  type: GET_CART_ITEM,
  payload,
})

export const addCartItem = (payload) => ({
  type: ADD_CART_ITEM,
  payload,
});

export const deleteCartItem = (payload) => ({
  type: DELETE_CART_ITEM,
  payload,
})

export const deleteCartItems = (payload) => ({
  type: DELETE_CART_ITEMS,
  payload,
})

export const updateCartItem = (payload) => ({
  type: UPDATE_CART_ITEM,
  payload,
})

export const selectedRestaurantForCart = (payload) => ({
  type: SELECT_RESTAURANT_FOR_CART,
  payload
})
