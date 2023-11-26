import {
  ADD_CART_ITEM,
  UPDATE_CART_ITEM,
  GET_CART_ITEM,
  DELETE_CART_ITEM,
  DELETE_CART_ITEMS,
  SELECT_RESTAURANT_FOR_CART,
  SET_RESTAURANT_CART,
  UPDATE_CART_TOTAL
} from "../Types/CartTypes";

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

export const setRestaurantCart = (payload) => ({
  type: SET_RESTAURANT_CART,
  payload
})

export const updateCartTotal = (payload) => ({
  type: UPDATE_CART_TOTAL,
  payload
})