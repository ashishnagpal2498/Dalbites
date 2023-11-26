import { GET_ORDER, SET_ORDER_LOADING, UPDATE_ORDER_STATUS } from "../Types/RestaurantOrderTypes";

export const getOrder = (payload) => ({
  type: GET_ORDER,
  payload,
});

export const setOrderLoading = (payload) => ({
  type: SET_ORDER_LOADING,
  payload,
});

export const updateOrderStatus = (payload) => ({
  type: UPDATE_ORDER_STATUS,
  payload,
})

