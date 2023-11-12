import React, { useEffect, useState } from "react";
import CardItem from "../src/Components/CartItem";
import tw from "twrnc";
import { Pressable, ScrollView, StyleSheet, Text, View } from "react-native";

const Cart = () => {
    const data = [
        {
            id: 2,
            name: "Dosa",
            description: "Tmp desc",
            price: 7,
            quantity: 1,
        },
        {
            id: 7,
            name: "Pav Bhaji",
            price: 7.5,
            description: "Made with vegetables",
            quantity: 2,
        },
        {
            id: 10,
            name: "Samosa",
            price: 6,
            description: "Made with potatoes",
            quantity: 3,
        },
        {
            id: 15,
            name: "Vada Pav",
            price: 5,
            description: "Made with potatoes",
            quantity: 2,
        }
    ];

    const placeOrder = () => {
        //place order login here
    }

    return(
        <View style={tw`flex-1 justify-center items-center bg-white`}>
            <ScrollView>
                {data.length == 0 ? <Text style={tw`text-black text-lg font-semibold text-center justify-center content-center my-70`}>Your cart is empty</Text>:
                data.map((item) => <CardItem key={item.id} cardData={item}></CardItem>)
                }
            </ScrollView> 
            <View style={styles.container}>
                <View style={{flexDirection:'row'}}>
                    <View style={{flexDirection:'column', marginLeft: '8px'}}>
                        <Text style={tw`text-black text-lg font-semibold`}>Sub Total: value</Text>
                        <Text style={tw`text-black text-lg font-semibold`}>Taxes: value</Text>
                        <Text style={tw`text-black text-lg font-semibold`}>Grand Total: value</Text>
                    </View>
                    <View style={{flexDirection:'column'}}>
                        <Pressable style={tw`bg-yellow-500 rounded-lg py-2 pl-12`} onPress={placeOrder}>
                            <Text style={tw`text-black text-lg font-semibold px-6 -pl-9`}>Place Order</Text>
                        </Pressable>
                        <Text style={tw`text-black text-lg font-semibold py-3 pl-12`}>Wait Time: value</Text>
                    </View>
                </View>
            </View>
        </View>
    );
};


const styles = StyleSheet.create({
    container: {
        marginBottom: 20,
        marginTop: 10
    }
});

export default Cart;