import React from "react";
import { View, Text, StyleSheet, Image } from "react-native";
import { FlatList, ScrollView } from "react-native-gesture-handler";

const OrderDetails = ({ route }) => {
  const order = route.params.order;
  console.log("Order Details page --> ", order);
  return (
    <ScrollView style={styles.container}>
      <View style={styles.restaurantContainer}>
        <Image
          style={styles.restaurantImage}
          source={{ uri: order.restaurantImage }}
        />
        <View style={styles.restaurantNameContainer}>
          <Text style={styles.restaurantName}>{order.restaurantName}</Text>
        </View>
      </View>

      <View style={styles.allOrdersContainer}>
        <View style={styles.HeaderContainer}>
          <Text style={styles.orderHeaderText}>Your Order</Text>
          <Text style={styles.orderHeaderText}>Order ID : {order.orderId}</Text>
        </View>

        <View style={styles.orderCardsContainer}>
          {order.orderItems.map((orderItemData) => (
            <View style={styles.orderCard} key={orderItemData.orderItemId}>
              <View style={styles.itemImageContainer}>
                <Image
                  style={styles.orderItemImage}
                  source={{ uri: orderItemData.item.menu_image }}
                />
              </View>
              <View style={styles.orderDetails}>
                <Text style={styles.orderItemNameText}>
                  {orderItemData.item.name}
                </Text>
                <Text style={styles.orderCardText}>
                  Cost per item: $ {orderItemData.item.price}
                </Text>
                <Text style={styles.orderCardText}>
                  Quantity: {orderItemData.quantity}
                </Text>
                <Text style={styles.orderCardText}>
                  Total Cost: ${" "}
                  {Number(
                    orderItemData.item.price * orderItemData.quantity
                  ).toPrecision(4)}
                </Text>
              </View>
            </View>
          ))}
        </View>
      </View>

      <View style={styles.orderSummaryContainer}>
        <View style={styles.HeaderContainer}>
          <Text style={styles.orderHeaderText}>Order Summary</Text>
        </View>

        <View style={styles.orderRow1}>
          <Text style={styles.orderRow1Text}>
            Date: {new Date(order.createdAt).toLocaleDateString()}
          </Text>
          <Text style={styles.orderRow1Text}>
            Time: {new Date(order.createdAt).toLocaleTimeString()}
          </Text>
        </View>

        <Text style={styles.orderSummaryText}>
          Special Instruction: {order.specialInstruction}
        </Text>
        <Text style={styles.orderSummaryText}>
          Subtotal: $ {order.totalAmount}
        </Text>

        <Text style={styles.orderSummaryText}>
          Taxes: $ {Number(order.totalAmount * 0.15).toPrecision(4)}
        </Text>
        <Text style={styles.orderSummaryText}>
          Grand Total: $ {Number(order.totalAmount * 1.15).toPrecision(4)}
        </Text>

        <View style={styles.orderRow2}>
          <Text style={styles.orderRow2Text}>Status: {order.status}</Text>
          <Text style={styles.orderRow2Text}>
            Estimated waiting time: 15 mins{" "}
          </Text>
        </View>
      </View>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    flexDirection: "column",
    backgroundColor: "white",
  },
  restaurantContainer: {
    width: "95%",
    aspectRatio: 2.2,
    overflow: "hidden",
    alignSelf: "center",
    borderRadius: 10,
    borderWidth: 3,
    marginVertical: 5,
  },
  restaurantImage: {
    width: "100%",
    height: "100%",
  },
  restaurantNameContainer: {
    width: "100%",
    position: "absolute",
    bottom: 0,
    alignSelf: "center",
  },
  restaurantName: {
    fontSize: 30,
    fontWeight: "bold",
    paddingHorizontal: 10,
    paddingVertical: 5,
    color: "#EAB308",
    backgroundColor: "rgba(0, 0, 0, 0.7)",
    textAlign: "left",
  },
  allOrdersContainer: {
    flexDirection: "column",
    width: "93%",
    alignSelf: "center",
    alignItems: "center",
    justifyContent: "space-evenly",
    backgroundColor: "white",
  },
  HeaderContainer: {
    flexDirection: "row",
    justifyContent: "space-between",
    width: "100%",
    paddingHorizontal: 5,
    borderBottomWidth: 1,
  },
  orderHeaderText: {
    fontSize: 21,
    fontWeight: "bold",
    margin: 5,
  },
  orderCardsContainer: {
    flexDirection: "column",
    paddingVertical: 5,
    width: "100%",
  },
  orderCard: {
    flexDirection: "row",
    width: "95%",
    alignSelf: "center",
    backgroundColor: "white",
    borderRadius: 10,
    elevation: 10,
    marginVertical: 5,
  },
  itemImageContainer: {
    flex: 1.2,
    width: "100%",
    aspectRatio: 1,
    overflow: "hidden",
    borderBottomLeftRadius: 10,
    borderTopLeftRadius: 10,
    padding: 5,
  },
  orderItemImage: {
    width: "100%",
    height: "100%",
    borderRadius: 10,
  },
  orderDetails: {
    flex: 3,
    justifyContent: "space-evenly",
    padding: 5,
  },
  orderItemNameText: {
    fontSize: 20,
    fontWeight: "bold",
  },
  orderCardText: {
    fontSize: 15,
    fontWeight: "500",
  },
  orderSummaryContainer: {
    width: "93%",
    alignSelf: "center",
    paddingHorizontal: 10,
  },
  orderRow1: {
    flexDirection: "row",
    justifyContent: "space-between",
    paddingVertical: 5,
  },
  orderRow1Text: {
    fontSize: 15,
    fontWeight: "400",
    paddingHorizontal: 5,
  },
  orderSummaryText: {
    fontSize: 18,
    fontWeight: "600",
    paddingHorizontal: 5,
  },
  orderRow2: {
    borderWidth: 1,
    marginBottom: 15,
    marginTop: 10,
    alignItems: "center",
    backgroundColor: "#EAB308",
  },
  orderRow2Text: {
    fontSize: 18,
    fontWeight: "600",
    paddingHorizontal: 5,
  },
});

export default OrderDetails;
