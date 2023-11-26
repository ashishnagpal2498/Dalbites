import { API_URL } from "../src/Utils/Api-Cred";

export const SignUpAPI = `${API_URL}api/signup`;

export const LoginAPI = `${API_URL}api/login`;

export const validateOTPAPI = `${API_URL}api/verify-account`;

export const forgetPasswordRequestAPI = `${API_URL}api/forget-password-request`;

export const forgetPasswordVerification = `${API_URL}api/forget-password-verification`;

export const SetupRestaurantAccountAPI = `${API_URL}api/restaurant/setup`;

export const BuildingListAPI = `${API_URL}api/get-buildings`;

export const viewAllRestaurantAPI = `${API_URL}api/restaurants`;

export const getAllBuildingsAPI = `${API_URL}api/get-buildings`;

export const getRestaurantByIdAPI = `${API_URL}api/restaurants`;

export const viewRestaurantMenuAPI = `${API_URL}api/restaurants`;

export const postReviewAPI = `${API_URL}api/review`;

export const getRestaurantReviewAPI = `${API_URL}api/review`;

export const getUserOrderAPI = `${API_URL}api/order/all`;

export const getReviewAPI = `${API_URL}api/review`;

export const updateReviewAPI = `${API_URL}api/review`;

export const getRestaurantMenuAPI = `${API_URL}api/restaurants`;

export const addRestaurantMenuItemAPI = `${API_URL}api/restaurants`;

export const updateRestaurantMenuItemAPI = `${API_URL}api/restaurants`;

export const deleteRestaurantMenuItemAPI = `${API_URL}api/restaurants`;

export const getAllOrdersForRestaurantAPI = `${API_URL}api/order/`;

export const updateOrderStatusForRestaurantAPI = `${API_URL}api/order/`;

export const getAllUserReviewAPI = `${API_URL}api/review`;

export const getUserDetailsAPI = `${API_URL}api/user`;

export const createOrderAPI = `${API_URL}api/order/create`;
