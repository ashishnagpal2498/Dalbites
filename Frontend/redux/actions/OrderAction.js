import {
  GET_USER_ORDER,
  ORDER_LOADING,
  POST_USER_ORDER,
  SET_SUCCESS_MESSAGE,
} from "../Types/OrderTypes";

export const setSuccessMessage = (payload) => ({
  type: SET_SUCCESS_MESSAGE,
  payload,
});

export const setOrderLoading = (payload) => ({
  type: ORDER_LOADING,
  payload,
});

export const getUserOrders = (token) => ({
  type: GET_USER_ORDER,
  payload: {
    token,
  },
});

export const createUserOrder = (payload) => ({
  type: POST_USER_ORDER,
  payload,
});
