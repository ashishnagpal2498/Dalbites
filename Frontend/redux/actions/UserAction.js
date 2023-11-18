import {
  GET_ALL_REVIEWS,
  GET_REVIEW,
  GET_USER,
  POST_REVIEW,
  SET_SUCCESS_MESSAGE,
} from "../Types/UserTypes";

export const postReview = (payload) => ({
  type: POST_REVIEW,
  payload,
});

export const getReview = (payload) => ({
  type: GET_REVIEW,
  payload,
});

export const setSuccessMessage = (payload) => ({
  type: SET_SUCCESS_MESSAGE,
  payload,
});

export const getUserDetails = (payload) => ({
  type: GET_USER,
  payload,
});

export const getAllUserReviews = (payload) => ({
  type: GET_ALL_REVIEWS,
  payload,
});
