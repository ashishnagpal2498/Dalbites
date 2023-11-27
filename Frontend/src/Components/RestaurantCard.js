import React from "react";
import { View, Text, StyleSheet, TouchableOpacity, Image } from "react-native";
import { useDispatch, useSelector } from "react-redux";
import { selectedRestaurantForCart } from "../../redux/actions/CartAction";

const RestaurantCard = ({ navigation, item }) => {
  const dispatch = useDispatch();

  const handleCardPress = (item) => {
    dispatch(selectedRestaurantForCart(item))
    navigation.navigate("RestaurantDetail", { id: item.id, title: item.name,  });
  };
  return (
    <TouchableOpacity onPress={() => handleCardPress({ name: item.name, id: item.id, address: item.address })}>
      <View style={styles.card}>
        <View style={styles.imageConatainer}>
          <Image
            style={styles.image}
            src={item.restaurantImage}
          />
          <View style={styles.textContainer}>
            <Text style={styles.restaurantName}>{item.name}</Text>
            <Text style={styles.buildingName}>{item.address}</Text>
            {/* <View style={styles.rowContainer}>
              <Text style={styles.rating}>Rating: {item.rating}</Text>
            </View> */}
          </View>
        </View>
        <View style={styles.moreDetailsContainer}>
        <Text style={styles.bestSellerText}>Rating: {item.rating}</Text>
          <Text style={styles.timeText}>Avg Prep Time: {item.estimatedDeliveryTime}</Text>
        </View>
      </View>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  card: {
    width: "100%",
    height: 190,
    backgroundColor: "#FFFFFF",
    borderRadius: 10,
    elevation: 5,
    marginBottom: 10,
  },
  imageConatainer: {
    width: "100%",
    height: 150,
    borderTopLeftRadius: 10,
    borderTopRightRadius: 10,
  },
  image: {
    width: "100%",
    height: 150,
    borderTopLeftRadius: 10,
    borderTopRightRadius: 10,
  },
  textContainer: {
    width: "100%",
    position: "absolute",
    bottom: 0,
    flexDirection: "row",
    justifyContent: "space-between",
    paddingHorizontal: 10,
    paddingVertical: 5,
    backgroundColor: "rgba(0, 0, 0, 0.5)",
  },
  restaurantName: {
    fontSize: 20,
    fontWeight: "bold",
    paddingHorizontal: 10,
    color: "#ecc94b",
    // backgroundColor: "rgba(0, 0, 0, 0.5)",
    textAlign: "left",
  },
  rowContainer: {
    flexDirection: "row",
    justifyContent: "space-between",
    paddingHorizontal: 10,
    paddingBottom: 5,
    backgroundColor: "rgba(0, 0, 0, 0.5)",
  },
  buildingName: {
    fontSize: 16,
    color: "#FFFFFF",
    textAlign: "center",
  },
  rating: {
    fontSize: 16,
    color: "#FFFFFF",
    textAlign: "center",
  },
  moreDetailsContainer: {
    width: "100%",
    position: "absolute",
    bottom: 0,
    flexDirection: "row",
    justifyContent: "space-between",
    padding: 10,
  },
  bestSellerText: {
    fontSize: 14,
    color: "#000",
  },
  timeText: {
    fontSize: 14,
    color: "#000",
  },
});

export default RestaurantCard;
