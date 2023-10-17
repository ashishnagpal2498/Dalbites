import {
  LOGIN,
  LOGOUT,
  SET_ERROR,
  SET_LOADING,
  SET_TOKEN,
  SIGNUP,
} from "../Types/AuthenticationTypes";

export const setLoading = (payload) => {
  return {
    type: SET_LOADING,
    payload,
  };
};

export const setToken = () => {
  return {
    type: SET_TOKEN,
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
