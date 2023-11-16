import React, { useState } from "react";
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
import { postReview } from "../redux/actions/UserAction";

const Review = ({ route }) => {
  const { orderId } = route.params;
  const dispatch = useDispatch();
  const review = useSelector((store) => store.user.review);
  const token = useSelector((store) => store.authentication.token);
  const loading = useSelector((store) => store.user.profileLoading);

  var date = moment().utcOffset("-04:00").format("DD-MM-YYYY hh:mm a");

  const starRatings = [1, 2, 3, 4, 5];

  const [rating, setRating] = useState(null);

  const handleStarPress = (index) => {
    setRating(index);
  };

  const [textDescription, setTextDescription] = useState("");

  const handleReviewChange = (text) => {
    setTextDescription(text);
  };

  const handleSaveReview = (rating, reviewComment) => {
    dispatch(postReview({ token, rating, reviewComment }));
  };

  return (
    <ScrollView style={styles.reviewScreenContainer}>
      <View style={styles.reviewInfoContainer}>
        <Text style={styles.infoText}>{orderId}</Text>
        <Text style={styles.infoText}>{date}</Text>
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
            placeholder="Add a review"
            onChangeText={(text) => handleReviewChange(text)}
          />
        </View>

        <View style={styles.saveButtonContainer}>
          <TouchableOpacity
            style={rating == null ? styles.disabledButton : styles.saveButton}
            onPress={() => handleSaveReview(rating, textDescription)}
            disabled={rating === null}
          >
            <Text style={styles.buttonText}>Save Review</Text>
          </TouchableOpacity>
        </View>
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
    fontSize: 20,
    fontWeight: "bold",
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

export default Review;
