import {
  POST_REVIEW_SUCCESS,
  POST_REVIEW_FAILURE,
  PROFILE_LOADING,
} from "../Types/UserTypes";

const initialState = {
  review: {},
  profileLoading: false,
};

export default (state = initialState, action) => {
  const { type, payload } = action;
  switch (type) {
    case PROFILE_LOADING:
      return {
        ...state,
        ...payload,
        reviewError: null,
      };
    case POST_REVIEW_SUCCESS:
      return {
        ...state,
        ...payload,
        reviewError: null,
      };
    case POST_REVIEW_FAILURE:
      return {
        ...state,
        ...payload,
      };
    default:
      return state;
  }
};
