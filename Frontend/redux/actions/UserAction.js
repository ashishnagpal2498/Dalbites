import { POST_REVIEW } from "../Types/UserTypes";

export const postReview = (payload) => ({
  type: POST_REVIEW,
  payload,
});
