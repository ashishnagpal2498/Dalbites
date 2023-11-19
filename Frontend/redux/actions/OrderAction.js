import { GET_USER_ORDER, ORDER_LOADING } from "../Types/OrderTypes";

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
