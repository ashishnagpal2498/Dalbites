import React, { useState } from "react";
import { CardField, confirmPayment, useStripe } from '@stripe/stripe-react-native';
import { Button, StyleSheet, Text, ToastAndroid, View } from "react-native";
import { API_URL } from "../src/Utils/Api-Cred";

const Payment = () => {
    const { initPaymentSheet, presentPaymentSheet } = useStripe();
    const [loading, setLoading] = useState(false);

    const fetchPaymentIntentClientSecret = async () => {
        const response = await fetch(`${API_URL}api/payments/create-payment-intent`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                currency: 'usd',
            }),
        });
        const {clientSecret} = await response.json();
    
        return clientSecret;
    };
    
    const handlePayPress = async () => {
        const billingDetails = {
            email: 'dalbitesuser@gmail.com',
        };

        const clientSecret = await fetchPaymentIntentClientSecret();

        const {paymentIntent, error} = await confirmPayment(clientSecret, {
            paymentMethodType: 'Card',
            paymentMethodData: {
              billingDetails,
            },
        });
      
        if (error) {
            console.log('Payment confirmation error', error);
            ToastAndroid.show(`Payment Failed ${error.localizedMessage}`, ToastAndroid.LONG)
        } else if (paymentIntent) {
            console.log('Success from promise', paymentIntent);
            ToastAndroid.show(`Payment Successful ${paymentIntent.id}`, ToastAndroid.LONG)
            // Please order API call goes here
        }
    };

    return (
        <View>
            <View style={styles.header}>
                <Text style={styles.name}>Enter Card Details</Text>
            </View>
            <CardField
                postalCodeEnabled={true}
                placeholders={{
                    number: '4242 4242 4242 4242',
                }}
                cardStyle={{
                    backgroundColor: '#FFFFFF',
                    textColor: '#000000',
                }}
                style={{
                    width: '100%',
                    height: 50,
                    marginVertical: 30,
                }}
            />
            <Button style={styles.payButton} onPress={handlePayPress} title="Pay" disabled={loading} />
        </View>
    );
}

const styles = StyleSheet.create({
    payButton: {
        width: '50%',
        backgroundColor: 'black'
    },
    header: {
        marginTop: 15,
    },
    name: {
        fontSize: 20,
        fontWeight: "bold",
        textAlign: "center",
    }
});
  
export default Payment;