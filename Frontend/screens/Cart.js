import React, { useEffect, useState } from "react";
import CardItem from "../src/Components/CartItem";
import tw from "twrnc";
import { Pressable, ScrollView, StyleSheet, Text, View } from "react-native";
import { useDispatch, useSelector } from "react-redux";
import { getCartItem } from "../redux/actions/RestaurantAction";
import { useIsFocused } from "@react-navigation/native";

const Cart = () => {
    let [subTotal, setSubTotal] = useState(0)
    let [maxWaitTime, setMaxWaitTime] = useState(0)
    const isFocused = useIsFocused();
    
    const data = useSelector((store) => store.restaurant.cartItems) || [];
    const restaurantData = useSelector((store) => store.restaurant.selectedRestaurantForCart) || [];

    const calculateCartTotal = () => {
        let total = 0;
        data.forEach((item) => total += (+item.price * +item.quantity))
        setSubTotal(total)
        if (data.length == 0) {
            setMaxWaitTime(0)
        }
        data.forEach((item) => {
            if (+item.time > maxWaitTime)
                setMaxWaitTime(+item.time)
        })
    }

    const placeOrder = () => {
        //place order login here
    }

    useEffect(() => {
        if (isFocused) {
            calculateCartTotal()
        }
    }, [isFocused, data]);

    return(
        <View style={tw`justify-center items-center bg-white`}>
            {data && data.length == 0 ?
                (<Text style={tw`text-black text-lg font-semibold text-center justify-center content-center my-70`}>Your cart is empty</Text>)
                :
                (
                    <View style={styles.headerContainer}>
                        <View style={styles.header}>
                            <View style={styles.extraheader}>
                                <Text style={styles.restaurantName}>{restaurantData.name}</Text>
                            </View>
                        </View>
                        <ScrollView style={styles.scrollViewContainer}>
                            {data.map((item, index) => <CardItem key={index} cardData={item}></CardItem>)}
                        </ScrollView>
                    </View>
                )
            }

            <View style={styles.container}>
                <View style={{flexDirection:'row'}}>
                    <View style={{flexDirection:'column', marginLeft: '8px'}}>
                        <Text style={tw`text-black text-lg font-semibold`}>Sub Total: ${ subTotal }</Text>
                        <Text style={tw`text-black text-lg font-semibold`}>Tax: ${ Number(subTotal * 0.15).toPrecision(2) }</Text>
                        <Text style={tw`text-green-600 text-lg font-semibold`}>Grand Total: ${ Number(subTotal * 1.15).toPrecision(2) }</Text>
                    </View>
                    <View style={{flexDirection:'column'}}>
                        <Pressable style={tw`bg-yellow-500 rounded-lg py-2 pl-12`} onPress={placeOrder}>
                            <Text style={tw`text-black text-lg font-semibold px-6 -pl-9`}>Place Order</Text>
                        </Pressable>
                        <Text style={tw`text-black text-lg font-semibold py-3 pl-12`}>Wait Time: {maxWaitTime} minutes</Text>
                    </View>
                </View>
            </View>
        </View>
    );
};


const styles = StyleSheet.create({
    container: {
        marginBottom: 100,
        marginTop: 10,
        textAlign: "center"
    },
    headerContainer: {
        width: '100%',
        textAlign: "center"
    },
    scrollViewContainer: {
        width: '100%',
        padding: 10
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
    restaurantName: {
        fontSize: 20,
        fontWeight: "bold",
        textAlign: "center",
    },
});

export default Cart;
