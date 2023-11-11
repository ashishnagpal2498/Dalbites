import React, { useEffect, useState } from "react";
import CardItem from "../src/Components/CartItem";
import tw from "twrnc";
import { ScrollView, View } from "react-native";

const Cart = () => {
    const data = [
        {
            id: 2,
            name: "Dosa",
            description: "Tmp desc",
            price: "7.50",
            quantity: "1",
        },
        {
            id: 7,
            name: "Pav Bhaji",
            price: "7.50",
            description: "Made with vegetables",
            quantity: "2",
        },
        {
            menuId: 10,
            name: "Samosa",
            price: "7.50",
            description: "Made with potatoes",
            quantity: "1",
        }
    ];

    return(
        <View style={tw`flex-1 justify-center items-center bg-white`}>
            <ScrollView>
                {data.length == 0 ? <Text style={tw`text-black text-lg font-semibold text-center justify-center content-center my-70`}>Your cart is empty</Text>:
                data.map((item) => <CardItem key={item.id} cardData={item}></CardItem>)
                }
            </ScrollView> 
        </View>
    );
};

export default Cart;