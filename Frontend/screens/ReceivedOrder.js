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
import { getOrder, updateOrderStatus } from "../redux/actions/RestaurantOrderAction";
import moment from "moment";
import { Alert } from "react-native";

const ReceivedOrder = () => {
  const dispatch = useDispatch();

  const loading = useSelector((store) => store.restaurantOrder.restaurantOrderLoading);
  const orders = useSelector((store) => store.restaurantOrder.restaurantOrder);
  const orderError = useSelector((store) => store.restaurantOrder.restaurantOrderError);

  useEffect(() => {
    dispatch(getOrder("all"));
  }, []);

  useEffect(() => {
    if (orderError != null) Alert.alert("Oops Error", orderError);
  }, [orderError]);

  const onRefresh = () => {
    dispatch(getOrder("all"));
  };

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
    };
    dispatch(updateOrderStatus(payload));
  };

  const renderItem = ({ item, index }) => {
    return (
      <View style={localStyles.itemCard}>
        <View style={[localStyles.txtContainer]}>
          <Text numberOfLines={1} style={localStyles.itemName}>
            {item.item.name}
          </Text>
        </View>
        <View style={[localStyles.txtContainer, { alignItems: "center" }]}>
          <Text numberOfLines={1} style={localStyles.itemQuantity}>
            {"Qty : "}
            {item.quantity}
          </Text>
        </View>
        <View style={[localStyles.txtContainer, { alignItems: "flex-end" }]}>
          <Text numberOfLines={1} style={localStyles.itemPrice}>
            {item.quantity} {"  "}x{"  "} $ {item.item.price}
          </Text>
        </View>
      </View>
    );
  };

  const renderFooter = (item,index) =>{
    return (
      <View style={localStyles.footerContaier}>
        <View style={{flexDirection:'row',gap:5}}>
            <Text>Payment Status :</Text>
            <Text
              style={{
                textAlign: "center",
                fontWeight: "bold",
              }}
            >
            {index % 2 == 0 ?  "Paid" : "UnPaid"}
            </Text>
            </View>
            <View style={{alignSelf:'center',flexDirection: "row",}}>
              <Text style={localStyles.idText}> Total : </Text>
              <Text style={localStyles.dataText}>
                {item.orderItems.reduce(
                  (accumulator, currentValue) =>
                    accumulator +
                    currentValue.item.price * currentValue.quantity,
                  0
                )}
              </Text>
            </View>
          </View>
    )
  }

  const renderOrder = ({ item, index, navigation }) => {
    return (
      <View style={localStyles.card}>
        <View style={localStyles.header}>
          <View style={localStyles.headerContainer}>
            <View style={localStyles.userNameCircle}>
              <Text style={localStyles.userTitleText}>{item?.name[0].toUpperCase()}</Text>
            </View>
            <View>
              <Text style={localStyles.userNameText}>{item?.name}</Text>
              <Text style={localStyles.orderTimeText}>
                {moment(item.createdAt).add(1, "days").calendar()}
              </Text>
            </View>
          </View>
            <View style={localStyles.detailContainer}>
              <Text style={localStyles.idText}>Order ID : </Text>
              <Text style={localStyles.dataText}> {item.orderId}</Text>
            </View>
                </View>
        <FlatList
          data={item.orderItems}
          renderItem={renderItem}
          ListFooterComponent={()=>renderFooter(item,index)}
          keyExtractor={(item) => item.item.id.toString()}
        />
        {item?.status == "DECLINED" || item?.status == "PICKED_UP" ? (
          <View style={localStyles.textContainer}>
            <Text>Order Status :</Text>
            <Text
              style={{
                textAlign: "center",
                fontWeight: "bold",
                color: item?.status == "DECLINED" ? "#FF6666" : "#006400",
              }}
            >
              Order {getOrderStatusText(item?.status)}
            </Text>
          </View>
        ) : (
          <View style={localStyles.btnContainer}>
            {item.status == "PENDING" && (
              <TouchableOpacity
                style={[
                  localStyles.btnStyle,
                  {
                    borderColor: "#90EE90",
                    borderWidth: 2,
                  },
                ]}
                onPress={() => changeOrderStatus(item.orderId, "ACCEPTED")}
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
            {(item.status == "ACCEPTED" ||
              item.status == "IN_QUEUE" ||
              item.status == "PREPARING" ||
              item.status == "READY_TO_PICKUP") && (
              <View style={localStyles.pickerContainer}>
                <Picker
                  selectedValue={item.status}
                  onValueChange={(itemValue, itemIndex) => {
                    changeOrderStatus(item.orderId, itemValue);
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
            {item.status !== "READY_TO_PICKUP" && (
              <TouchableOpacity
                style={[
                  localStyles.btnStyle,
                  {
                    backgroundColor: "#FF6666",
                  },
                ]}
                onPress={() => changeOrderStatus(item.orderId, "DECLINED")}
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
        keyExtractor={(item) => item.orderId.toString()}
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
  orderStatusText:{
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
  },
  headerContainer: {
    flexDirection: "row",
    gap: 10,
  },
  userNameCircle: {
    width:40,
    height:40,
    borderRadius: 25,
    backgroundColor: "white",
    padding: 10,
    alignItems:'center',
    justifyContent:'center',
  },
  userTitleText:{
    fontSize: 16,
    fontWeight: "bold",
    color: "#000000",
  },
  detailContainer: {
    flexDirection: "row",
  },
  footerContaier:{
    justifyContent:'space-between',
    alignItems:'center',
    flexDirection:'row',
    padding: 10,
    // backgroundColor: "#f1d981",
    backgroundColor:"#f5e4a5"

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
    // backgroundColor: "#ecc94b",
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
    // backgroundColor: "#f1d981",
    backgroundColor:"#f5e4a5",
    borderBottomLeftRadius: 10,
    borderBottomRightRadius: 10,
  },
});
