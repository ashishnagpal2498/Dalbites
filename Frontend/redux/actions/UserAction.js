import {
  GET_REVIEW,
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
