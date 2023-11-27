import React, { useEffect, useState } from "react";
import CardItem from "../src/Components/CartItem";
import tw from "twrnc";
import { Pressable, ScrollView, StyleSheet, Text, View, Image} from "react-native";
import { useDispatch, useSelector } from "react-redux";
import { getCartItem, updateCartTotal } from "../redux/actions/CartAction";
import { useIsFocused } from "@react-navigation/native";

const Cart = ({ navigation }) => {
  const dispatch = useDispatch();
  const subTotal = useSelector((store) => store.cart.subTotal) || 0;
  const maxWaitTime = useSelector((store) => store.cart.maxPreparationTime) || 0;
  const isFocused = useIsFocused();

  const data = useSelector((store) => store.cart.cartItems) || [];
  const restaurantData =
    useSelector((store) => store.cart.selectedRestaurantForCart) || [];

  const placeOrder = () => {
    console.log("cart data -->", data);
    const payloadData = {
      orderItems: [],
      restaurantId: "",
      status: "",
      totalAmount: "",
    };
    data.forEach((menuItem) => {
      const orderItem = { quantity: "", item: { id: "" } };
      orderItem["quantity"] = menuItem.quantity;
      orderItem["item"]["id"] = menuItem.id;
      payloadData.orderItems = [...payloadData.orderItems, orderItem];
    });
    payloadData.restaurantId = restaurantData.id;

    navigation.navigate("Payment", {
      amount: Number(subTotal * 1.15).toPrecision(4),
      payloadData,
    });
  };

  useEffect(() => {
    if (isFocused) {
      dispatch(updateCartTotal());
    }
  }, [isFocused, data]);

  return (
    <ScrollView style={styles.screenContainer}>

        {data && data.length == 0 ? (
          <View>
            <Text style={styles.emptyText}>
              Your cart is empty !!
            </Text>
          </View>
        ) : (
          <View>
        <View style={styles.restaurantOrderContainer}>

          <View style={styles.restaurantContainer}>
            <Image
              style={styles.restaurantImage}
              source={{ uri: restaurantData.Image}}
            />
            <View style={styles.restaurantNameContainer}>
              <Text style={styles.restaurantName}>{restaurantData.name}</Text>
            </View>
          </View>
          {/* <View style={styles.header}>
            <View style={styles.extraheader}>
              <Text style={styles.restaurantName}>{restaurantData.name}</Text>
            </View>
          </View> */}

          <View style={styles.allItemsContainer}>
            {data.map((item, index) => (
              <CardItem key={index} cardData={item}></CardItem>
            ))}
          </View>
        </View>

        <View style={styles.orderSummaryContainer}>

        <View style={styles.HeaderContainer}>
          <Text style={styles.orderHeaderText}>Order Summary</Text>
        </View>
        
        <View style={styles.orderSummaryTextContainer}>
          <Text style={styles.orderSummaryText}>
            Subtotal: $ {subTotal}
          </Text>
          <Text style={styles.orderSummaryText}>
            Taxes: $ {Number(subTotal * 0.15).toPrecision(4)}
          </Text>
          <Text style={styles.orderSummaryText}>
            Grand Total: $ {Number(subTotal * 1.15).toPrecision(4)}
          </Text>
          <Text style={styles.orderSummaryText}>
            Estimated waiting time: {maxWaitTime} minutes
          </Text>
        </View>

        <Pressable style={styles.placeOrderButton} onPress={placeOrder}>
          <Text style={styles.placeOrderText}>
            Place Order
          </Text>
        </Pressable>

      </View>

        </View>
        )}
      {/* </View> */}

      

    </ScrollView>
  );
};

const styles = StyleSheet.create({
  screenContainer:{
    flex: 1,
    flexDirection: "column",
    backgroundColor: "white",
  },
  emptyText:{
    textAlignVertical:"center",
    textAlign:"center",
    marginTop:300,
    fontSize: 30,
  },
  restaurantOrderContainer:{

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

  header: {
    padding: 0,
    margin: 7,
    marginRight: 5,
  },
  extraheader: {
    backgroundColor: "#EAB308",
    padding: 7,
    borderRadius: 10,
  },

  allItemsContainer:{
    flexDirection: "column",
    width: "93%",
    alignSelf: "center",
    alignItems: "center",
    justifyContent: "space-evenly",
    backgroundColor: "white",
  },
  orderSummaryContainer: {
    width: "93%",
    alignSelf: "center",
    paddingHorizontal: 10,
    paddingBottom:10,
  },
  HeaderContainer: {
    flexDirection: "row",
    justifyContent: "space-between",
    width: "100%",
    paddingHorizontal: 5,
    borderBottomWidth: 1,
  },
  orderHeaderText: {
    fontSize: 20,
    fontWeight: "bold",
    margin: 5,
  },
  orderSummaryTextContainer:{
    padding:5,
  },
  orderSummaryText: {
    fontSize: 17,
    fontWeight: "600",
    paddingHorizontal: 5,
  },
  placeOrderButton:{
    marginTop: 7,
    width: "90%",
    alignSelf: "center",
    alignItems: "center",
  },
  placeOrderText:{
    backgroundColor: "#EAB308",
    paddingVertical: 7,
    paddingHorizontal: 15,
    borderRadius: 10,
    fontWeight:"bold",
    fontSize: 21,

  }
});

export default Cart;
