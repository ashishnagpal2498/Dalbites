import React, { useEffect } from "react";
import {
  CardField,
  confirmPayment,
  useStripe,
} from "@stripe/stripe-react-native";
import tw from "twrnc";
import { Button, StyleSheet, Text, ToastAndroid, View } from "react-native";
import { API_URL } from "../src/Utils/Api-Cred";
import { useDispatch, useSelector } from "react-redux";
import {
  createUserOrder,
  setOrderLoading,
  setSuccessMessage,
} from "../redux/actions/OrderAction";
import Loading from "./Loading";

const Payment = ({ route, navigation }) => {
  const { payloadData, amount } = route.params;
  const { initPaymentSheet, presentPaymentSheet } = useStripe();
  const loading = useSelector((store) => store.order.orderLoading);
  const dispatch = useDispatch();
  const token = useSelector((store) => store.authentication.token);
  const successMessage = useSelector((store) => store.order.successMessage);
  const order = useSelector((store) => store.order.order);
  payloadData["totalAmount"] = amount;
  payloadData["status"] = "IN_QUEUE";

  console.log("Payload Data --> ", payloadData);

  // useEffect(() => {
  //   dispatch(setOrderLoading({ loading: true }));
  //   setTimeout(() => dispatch(setOrderLoading({ loading: false })), 2000);
  // }, []);
  const fetchPaymentIntentClientSecret = async () => {
    const response = await fetch(
      `${API_URL}api/payments/create-payment-intent`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          currency: "usd",
        }),
      }
    );
    const { clientSecret } = await response.json();

    return clientSecret;
  };

  const handlePayPress = async () => {
    dispatch(setOrderLoading({ loading: true }));
    const billingDetails = {
      email: "dalbitesuser@gmail.com",
    };

    const clientSecret = await fetchPaymentIntentClientSecret();

    const { paymentIntent, error } = await confirmPayment(clientSecret, {
      paymentMethodType: "Card",
      paymentMethodData: {
        billingDetails,
      },
    });
    dispatch(setOrderLoading({ loading: false }));
    if (error) {
      console.log("Payment confirmation error", error);
      ToastAndroid.show(
        `Payment Failed ${error.localizedMessage}`,
        ToastAndroid.LONG
      );
    } else if (paymentIntent) {
      console.log("Success from promise", paymentIntent);
      ToastAndroid.show(
        `Payment Successful ${paymentIntent.id} Placing order`,
        ToastAndroid.LONG
      );
      dispatch(createUserOrder({ token, order: payloadData }));
    }
  };

  if (successMessage) {
    setTimeout(() => {
      dispatch(setSuccessMessage({ successMessage: null }));
      navigation.navigate("OrderDetails", { order });
    }, 4000);
  }
  if (loading) {
    return <Loading />;
  }
  return (
    <View>
      <View style={styles.header}>
        <Text style={styles.name}>Enter Card Details</Text>
      </View>
      <CardField
        postalCodeEnabled={true}
        placeholders={{
          number: "4242 4242 4242 4242",
        }}
        cardStyle={{
          backgroundColor: "#FFFFFF",
          textColor: "#000000",
        }}
        style={{
          width: "100%",
          height: 50,
          marginVertical: 30,
        }}
      />
      <Button
        style={styles.payButton}
        onPress={handlePayPress}
        title="Pay"
        disabled={loading}
      />
      <Text style={tw`text-green-600 mt-5`}>
        {successMessage && successMessage}
      </Text>
    </View>
  );
};

const styles = StyleSheet.create({
  payButton: {
    width: "50%",
    backgroundColor: "black",
  },
  header: {
    marginTop: 15,
  },
  name: {
    fontSize: 20,
    fontWeight: "bold",
    textAlign: "center",
  },
});

export default Payment;
