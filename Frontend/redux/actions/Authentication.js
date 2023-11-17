import {
  LOGIN,
  LOGOUT,
  SET_ERROR,
  SET_LOADING,
  SET_TOKEN,
  SIGNUP,
  VALIDATE_OTP,
  SET_SUCCESS_MESSAGE,
  FORGET_PASSWORD,
  SETUP_RESTAURANT_ACCOUNT
} from "../Types/AuthenticationTypes";

export const setLoading = (payload) => {
  return {
    type: SET_LOADING,
    payload,
  };
};

export const setToken = (payload) => {
  return {
    type: SET_TOKEN,
    payload: { 
      token: payload.token,
      isRestaurant: payload?.isRestaurant || false,
      restaurantId: payload.restaurantId
    },
  };
};

export const setError = (payload) => {
  return {
    type: SET_ERROR,
    payload,
  };
};

export const login = (payload) => ({
  type: LOGIN,
  payload,
});

export const logout = () => ({
  type: LOGOUT,
});

export const signUp = (payload) => ({
  type: SIGNUP,
  payload,
});

export const validateOTP = (payload) => ({
  type: VALIDATE_OTP,
  payload,
});

export const setSuccessMessage = (payload) => ({
  type: SET_SUCCESS_MESSAGE,
  payload,
});

export const forgetPassword = (payload) => ({
  type: FORGET_PASSWORD,
  payload,
});

export const setupRestaurantAccount = (payload) => ({
  type: SETUP_RESTAURANT_ACCOUNT,
  payload,
});
