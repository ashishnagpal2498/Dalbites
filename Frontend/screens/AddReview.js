import React, { useEffect, useState } from "react";
import {
  View,
  Text,
  TouchableOpacity,
  StyleSheet,
  TextInput,
} from "react-native";
import { ScrollView } from "react-native-gesture-handler";
import moment from "moment";
import { useDispatch, useSelector } from "react-redux";
import {
  getReview,
  postReview,
  setSuccessMessage,
} from "../redux/actions/UserAction";
import Loading from "./Loading";
import { getRestaurantById } from "../redux/actions/RestaurantAction";
import tw from "twrnc";

const AddReview = ({ route, navigation }) => {
  const { restaurantId } = route.params;
  const dispatch = useDispatch();
  const review = useSelector((store) => store.user.review);
  const token = useSelector((store) => store.authentication.token);
  const profileLoading = useSelector((store) => store.user.profileLoading);
  const restaurant = useSelector((store) => store.restaurant.restaurant);
  const restaurantLoading = useSelector(
    (store) => store.restaurant.restaurantLoading
  );
  const [reviewComment, setReviewComment] = useState("");
  const successMessage = useSelector((store) => store.user.successMessage);
  const handleReviewChange = (text) => {
    setReviewComment(text);
  };

  const handleSaveReview = (rating, reviewComment) => {
    dispatch(
      postReview({ token, restaurantId, rating, reviewComment, review })
    );
  };

  if (successMessage) {
    setTimeout(() => {
      dispatch(setSuccessMessage({ successMessage: null }));
      navigation.navigate("OrderHistory");
    }, 2000);
  }

  const starRatings = [1, 2, 3, 4, 5];

  const [rating, setRating] = useState(0);

  const handleStarPress = (index) => {
    setRating(index);
  };

  useEffect(() => {
    dispatch(getRestaurantById({ id: restaurantId, token }));
    dispatch(getReview({ id: restaurantId, token }));
  }, []);

  useEffect(() => {
    if (review) {
      setReviewComment(review.reviewComment);
      setRating(review.rating);
    } else {
      setReviewComment("");
      setRating(0);
    }
  }, [review]);

  if (restaurantLoading || profileLoading) {
    return <Loading />;
  }

  // if (review) {
  //   setTimeout(() => navigation.navigate("Profile"), 3000);
  // }
  return (
    <ScrollView style={styles.reviewScreenContainer}>
      <View style={styles.reviewInfoContainer}>
        <Text style={styles.infoText}>{restaurant.name}</Text>
        <Text style={styles.infoText}>
          {review &&
            new Date(review.createdAt).toLocaleDateString() +"  "+ 
              new Date(review.createdAt).toLocaleTimeString()}
        </Text>
      </View>

      <View style={styles.reviewContainer}>
        <View style={styles.ratingBox}>
          {starRatings.map((index) => (
            <TouchableOpacity
              key={index}
              onPress={() => handleStarPress(index)}
            >
              <Text
                style={[
                  styles.stars,
                  { color: rating + 1 <= index ? "grey" : "#EAB308" },
                ]}
              >
                {" "}
                ★{" "}
              </Text>
            </TouchableOpacity>
          ))}
        </View>

        <View style={styles.descriptionBox}>
          <TextInput
            style={styles.descriptionText}
            multiline={true}
            numberOfLines={4}
            placeholder="Add review comment . . ."
            value={reviewComment}
            onChangeText={(text) => handleReviewChange(text)}
          />
        </View>

        <View style={styles.saveButtonContainer}>
          <TouchableOpacity
            style={rating == null ? styles.disabledButton : styles.saveButton}
            onPress={() => handleSaveReview(rating, reviewComment)}
            disabled={rating === null}
          >
            <Text style={styles.buttonText}>
              {review ? "Update" : "Save"} Review
            </Text>
          </TouchableOpacity>
        </View>
        <Text style={tw`text-green-600 mt-4`}>
          {successMessage && successMessage}
        </Text>
      </View>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  reviewScreenContainer: {
    flex: 1,
    flexDirection: "column",
    backgroundColor: "white",
  },
  reviewInfoContainer: {
    margin: 5,
    marginHorizontal: 10,
    flexDirection: "row",
    justifyContent: "space-between",
    backgroundColor: "black",
  },
  infoText: {
    fontSize: 16,
    color: "white",
    padding: 3,
    paddingHorizontal: 7,
  },
  reviewContainer: {
    borderWidth: 1,
    marginHorizontal: 10,
    padding: 10,
    paddingTop: 2,
  },
  ratingBox: {
    flexDirection: "row",
    alignSelf: "center",
    margin: 5,
  },
  stars: {
    fontSize: 25,
    paddingBottom: 6,
  },
  saveButtonContainer: {
    marginTop: 7,
    width: "90%",
    alignSelf: "center",
    alignItems: "center",
  },
  saveButton: {
    backgroundColor: "#EAB308",
    paddingVertical: 7,
    paddingHorizontal: 15,
    borderRadius: 10,
  },
  disabledButton: {
    backgroundColor: "grey",
    paddingVertical: 7,
    paddingHorizontal: 15,
    borderRadius: 10,
  },
  buttonText: {
    fontSize: 18,
    textAlign: "center",
  },
  descriptionBox: {
    width: "100%",
    borderWidth: 1,
    alignSelf: "center",
    flexDirection: "column",
  },
  descriptionText: {
    padding: 3,
    fontSize: 14,
    textAlignVertical: "top",
  },
});

export default AddReview;
