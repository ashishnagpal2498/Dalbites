import {
  FlatList,
  StyleSheet,
  Text,
  View,
  TouchableOpacity,
} from "react-native";
import React, { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import Loading from "./Loading";
import { Picker } from "@react-native-picker/picker";
import {
  getOrder,
  updateOrderStatus,
} from "../redux/actions/RestaurantOrderAction";
import moment from "moment";
import { Alert } from "react-native";

const ReceivedOrder = () => {
  const dispatch = useDispatch();

  const loading = useSelector(
    (store) => store.restaurantOrder.restaurantOrderLoading
  );
  const orders = useSelector((store) => store.restaurantOrder.restaurantOrder);
  const token = useSelector((store) => store.authentication.token);
  const orderError = useSelector(
    (store) => store.restaurantOrder.restaurantOrderError
  );

  useEffect(() => {
    dispatch(getOrder({ queryType: "all", token }));
  }, []);

  useEffect(() => {
    if (orderError != null) Alert.alert("Oops Error", orderError);
  }, [orderError]);

  const onRefresh = () => {
    dispatch(getOrder({ queryType: "all", token }));
  };

  console.log("Orders", orders);
  const getOrderStatusText = (status) => {
    switch (status) {
      case "ACCEPTED":
        return "Accepted";
      case "IN_QUEUE":
        return "In-Queue";
      case "PREPARING":
        return "Preparing";
      case "READY_TO_PICKUP":
        return "Ready For PickUp";
      case "PICKED_UP":
        return "Picked Up";
      case "DECLINED":
        return "Declined";
      default:
        break;
    }
  };

  const changeOrderStatus = (id, status) => {
    console.log(id, status);
    let payload = {
      id: id,
      body: { status: status },
      token,
    };
    console.log(payload, "---> Payload");
    dispatch(updateOrderStatus(payload));
  };

  const renderOrderItem = ({ item, index }) => {
    const orderItem = { ...item };
    console.log("OrderItem ---> ", orderItem);
    return (
      <View style={localStyles.itemCard}>
        <View style={[localStyles.txtContainer]}>
          <Text numberOfLines={1} style={localStyles.itemName}>
            {orderItem.item.name}
          </Text>
        </View>
        <View style={[localStyles.txtContainer, { alignItems: "center" }]}>
          <Text numberOfLines={1} style={localStyles.itemQuantity}>
            {"Qty : "}
            {orderItem.quantity}
          </Text>
        </View>
        <View style={[localStyles.txtContainer, { alignItems: "flex-end" }]}>
          <Text numberOfLines={1} style={localStyles.itemPrice}>
            {orderItem.quantity} {"  "}x{"  "} $ {orderItem.item.price}
          </Text>
        </View>
      </View>
    );
  };

  const renderFooter = (item, index) => {
    const order = { ...item };
    console.log("Render Footer --> order ", order);
    return (
      <View style={localStyles.footerContaier}>
        <View style={{ flexDirection: "row", gap: 5 }}>
          <Text>Payment Status :</Text>
          <Text
            style={{
              textAlign: "center",
              fontWeight: "bold",
            }}
          >
            {index % 2 == 0 ? "Paid" : "UnPaid"}
          </Text>
        </View>
        <View style={{ alignSelf: "center", flexDirection: "row" }}>
          <Text style={localStyles.idText}> Total : </Text>
          <Text style={localStyles.dataText}>{order.totalAmount}</Text>
        </View>
      </View>
    );
  };

  const renderOrder = ({ item, index }) => {
    const order = { ...item };
    console.log("Order --> here --", order);
    return (
      <View style={localStyles.card}>
        <View style={localStyles.header}>
          <View style={localStyles.headerContainer}>
            <View style={localStyles.userNameCircle}>
              <Text style={localStyles.userTitleText}>
                {order?.userName?.[0].toUpperCase()}
              </Text>
            </View>
            <View>
              <Text style={localStyles.userNameText}>{order?.userName}</Text>
              <Text style={localStyles.orderTimeText}>
                {moment(order?.createdAt).format("LL")}
              </Text>
              <Text style={localStyles.orderTimeText}>
                {moment(order?.createdAt).format("LT")}
              </Text>
            </View>
          </View>
          <View style={localStyles.detailContainer}>
            <Text style={localStyles.idText}>Order ID : </Text>
            <Text style={localStyles.dataText}> {order.orderId}</Text>
          </View>
        </View>
        <FlatList
          data={order.orderItems}
          renderItem={renderOrderItem}
          ListFooterComponent={() => renderFooter(order, index)}
          keyExtractor={(orderItem) => orderItem.orderItemId.toString()}
        />
        {order?.status == "DECLINED" || order?.status == "PICKED_UP" ? (
          <View style={localStyles.textContainer}>
            <Text>Order Status :</Text>
            <Text
              style={{
                textAlign: "center",
                fontWeight: "bold",
                color: order?.status == "DECLINED" ? "#FF6666" : "#006400",
              }}
            >
              Order {getOrderStatusText(order?.status)}
            </Text>
          </View>
        ) : (
          <View style={localStyles.btnContainer}>
            {order.status == "PENDING" && (
              <TouchableOpacity
                style={[
                  localStyles.btnStyle,
                  {
                    borderColor: "#90EE90",
                    borderWidth: 2,
                  },
                ]}
                onPress={() => changeOrderStatus(order.orderId, "ACCEPTED")}
              >
                <Text
                  style={[
                    localStyles.btnText,
                    {
                      color: "#90EE90",
                    },
                  ]}
                >
                  Accept Order
                </Text>
              </TouchableOpacity>
            )}
            {(order.status == "ACCEPTED" ||
              order.status == "IN_QUEUE" ||
              order.status == "PREPARING" ||
              order.status == "READY_TO_PICKUP") && (
              <View style={localStyles.pickerContainer}>
                <Picker
                  selectedValue={order.status}
                  onValueChange={(itemValue, itemIndex) => {
                    changeOrderStatus(order.orderId, itemValue);
                  }}
                  style={localStyles.dropDownStyle}
                  mode={"dropdown"}
                >
                  <Picker.Item label="Accepted" value="ACCEPTED" />
                  <Picker.Item label="In-Queue" value="IN_QUEUE" />
                  <Picker.Item label="Preparing" value="PREPARING" />
                  <Picker.Item
                    label="Ready For PickUp"
                    value="READY_TO_PICKUP"
                  />
                  <Picker.Item label="Picked-Up" value="PICKED_UP" />
                </Picker>
              </View>
            )}
            {order.status === "IN_QUEUE" && (
              <TouchableOpacity
                style={[
                  localStyles.btnStyle,
                  {
                    backgroundColor: "#FF6666",
                  },
                ]}
                onPress={() => changeOrderStatus(order.orderId, "DECLINED")}
              >
                <Text style={localStyles.btnText}>Cancel Order</Text>
              </TouchableOpacity>
            )}
          </View>
        )}
      </View>
    );
  };
  if (loading) {
    return <Loading />;
  }
  return (
    <View>
      <FlatList
        style={localStyles.flatlistConatiner}
        refreshing={loading}
        onRefresh={onRefresh}
        data={orders}
        renderItem={renderOrder}
        keyExtractor={(order) => order.orderId.toString()}
      />
    </View>
  );
};

export default ReceivedOrder;

const localStyles = StyleSheet.create({
  flatlistConatiner: {
    margin: 10,
  },
  orderTimeText: {
    fontSize: 12,
    fontWeight: "600",
    color: "#000000",
  },
  orderStatusText: {
    fontSize: 14,
    fontWeight: "500",
    color: "#000000",
  },
  card: {
    backgroundColor: "#FFFFFF",
    borderRadius: 10,
    elevation: 5,
    marginBottom: 10,
    marginHorizontal: 5,
  },
  header: {
    padding: 10,
    backgroundColor: "#ecc94b",
    textAlign: "left",
    borderTopLeftRadius: 10,
    borderTopRightRadius: 10,
    justifyContent: "space-between",
    flexDirection: "row",
    alignItems: "center",
    flex: 1,
  },
  headerContainer: {
    flexDirection: "row",
    gap: 10,
    // flex: 1,
  },
  userNameCircle: {
    width: 40,
    height: 40,
    borderRadius: 25,
    backgroundColor: "white",
    padding: 10,
    alignItems: "center",
    justifyContent: "center",
    alignSelf: "center",
  },
  userTitleText: {
    fontSize: 16,
    fontWeight: "bold",
    color: "#000000",
  },
  detailContainer: {
    flexDirection: "row",
    alignItems: "flex-end",
    // flex: 1,
  },
  footerContaier: {
    justifyContent: "space-between",
    alignItems: "center",
    flexDirection: "row",
    padding: 10,
    // backgroundColor: "#f1d981",
    backgroundColor: "#f5e4a5",
  },
  idText: {
    fontSize: 14,
    fontWeight: "bold",
    color: "rgba(0, 0, 0, 0.5)",
  },
  dataText: {
    fontSize: 14,
    fontWeight: "bold",
    color: "rgba(0, 0, 0, 1)",
  },
  userNameText: {
    fontSize: 16,
    fontWeight: "bold",
    color: "#000000",
  },
  itemCard: {
    padding: 10,
    backgroundColor: "#FFFFFF",
    textAlign: "left",
    justifyContent: "space-between",
    flexDirection: "row",
  },
  txtContainer: {
    flex: 1,
  },
  itemName: {
    flex: 1,
    fontSize: 14,
    fontWeight: "500",
    color: "#000000",
  },
  itemQuantity: {
    fontSize: 14,
    fontWeight: "500",
    color: "#000000",
  },
  itemPrice: {
    fontSize: 14,
    fontWeight: "500",
    color: "#000000",
  },
  btnContainer: {
    flexDirection: "row",
    justifyContent: "space-between",
    padding: 10,
  },
  btnStyle: {
    width: "48%",
    borderRadius: 10,
    justifyContent: "center",
    alignItems: "center",
    paddingVertical: 15,
  },
  btnText: {
    fontSize: 14,
    fontWeight: "bold",
    color: "#FFFFFF",
  },
  pickerContainer: {
    width: "48%",
    justifyContent: "center",
    alignItems: "center",
    borderWidth: 1,
    borderRadius: 10,
  },
  dropDownStyle: {
    width: "100%",
    borderRadius: 20,
    borderWidth: 1,
  },
  textContainer: {
    flexDirection: "row",
    alignItems: "center",
    gap: 10,
    paddingBottom: 10,
    paddingHorizontal: 10,
    backgroundColor: "#f5e4a5",
    borderBottomLeftRadius: 10,
    borderBottomRightRadius: 10,
  },
});
