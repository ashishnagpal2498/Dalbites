import React, { useEffect } from "react";
import { View, Text, TouchableOpacity, StyleSheet, Image } from "react-native";
import { ScrollView } from "react-native-gesture-handler";
import { useDispatch, useSelector } from "react-redux";
import Loading from "./Loading";
import { getUserOrders } from "../redux/actions/OrderAction";
import { SafeAreaView } from "react-native-safe-area-context";

const Orders = ({ navigation }) => {
  const dispatch = useDispatch();
  const orders = useSelector((store) => store.order.orders);
  const token = useSelector((store) => store.authentication.token);
  const loading = useSelector((store) => store.order.orderLoading);

  const handlereview = (restaurantId) => {
    navigation.navigate("AddReview", { restaurantId });
  };

  useEffect(() => {
    dispatch(getUserOrders(token));
  }, []);

  if (loading) {
    return <Loading />;
  }
  return (
    <ScrollView style={styles.container}>
      <SafeAreaView>
        <View style={styles.ordercontainer}>
          {orders.map((order, index) => (
            <TouchableOpacity
              onPress={() => navigation.navigate("OrderDetails", { order })}
              style={styles.ordercard}
              key={index}
            >
              <View style={styles.row1}>
                <Text style={styles.row1text}>Order ID: {order.orderId}</Text>
                <Text style={styles.row1text}>
                  Date: {new Date(order.createdAt).toLocaleDateString()}
                </Text>
              </View>

              <View style={styles.row2}>
                <Text style={styles.row2text}>{order.restaurantName}</Text>
                <Text style={styles.row2text}>Cost: ${order.totalAmount}</Text>
              </View>

              <View style={styles.row3}>
                <Text style={styles.row3text}>Order: </Text>
                <Text style={styles.row3text}>
                  {order.orderItems.map(
                    (menuItem, i) =>
                      `${menuItem.item.name}(${menuItem.quantity})${
                        i === order.orderItems.length - 1 ? "" : ", "
                      }`
                  )}
                </Text>
              </View>

              <View style={styles.buttonsContainer}>
                <Text> Status: {order.status.split("_").join(" ")} </Text>
                <TouchableOpacity
                  style={styles.reviewButton}
                  onPress={() => handlereview(order.restaurantId)}
                >
                  <Text style={styles.reviewtext}>Review restaurant</Text>
                </TouchableOpacity>
              </View>
            </TouchableOpacity>
          ))}
        </View>
      </SafeAreaView>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    flexDirection: "column",
    backgroundColor: "lightgrey",
  },
  nametext: {
    fontSize: 20,
    fontWeight: "bold",
    textAlign: "left",
  },
  bannertext: {
    fontSize: 15,
    textAlign: "left",
  },
  emailtext: {
    fontSize: 15,
    textAlign: "left",
  },
  profilebuttonscontainer: {
    flexDirection: "row",
    justifyContent: "space-evenly",
    borderWidth: 1,
    borderTopWidth: 0,
  },
  button: {
    backgroundColor: "#EAB308",
    paddingVertical: 7,
    paddingHorizontal: 15,
    borderRadius: 10,
    marginVertical: 10,
  },
  buttonText: {
    fontSize: 18,
    fontWeight: "bold",
    textAlign: "center",
  },
  ordercontainer: {
    padding: 5,
  },
  ordercard: {
    backgroundColor: "white",
    width: "95%",
    alignSelf: "center",
    padding: 10,
    marginVertical: 5,
    borderRadius: 10,
    elevation: 20,
    shadowColor: "black",
  },
  row1: {
    flexDirection: "row",
    justifyContent: "space-between",
    backgroundColor: "black",
  },
  row1text: {
    fontSize: 16,
    color: "white",
    padding: 3,
    paddingHorizontal: 7,
  },
  row2: {
    flexDirection: "row",
    justifyContent: "space-between",
  },
  row2text: {
    fontSize: 20,
    fontWeight: "bold",
    color: "black",
    padding: 3,
  },
  row3: {
    flexDirection: "row",
  },
  row3text: {
    fontSize: 16,
  },
  buttonsContainer: {
    marginTop: 10,
    display: "flex",
    justifyContent: "space-between",
    flexDirection: "row",
  },
  reviewButton: {
    paddingVertical: 5,
    paddingHorizontal: 10,
    borderRadius: 7,
  },
  reviewtext: {
    fontSize: 12,
    color: "blue",
    textAlign: "center",
  },
});

export default Orders;
