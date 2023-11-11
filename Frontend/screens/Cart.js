import React, { useEffect, useState } from "react";
import CardItem from "../src/Components/CartItem";
import tw from "twrnc";
import { ScrollView, View } from "react-native";

const Cart = () => {
    const data = [
        {
            menuId: 2,
            name: "Dosa",
            description: "Made with rice",
            price: "7.50"
        },
        {
            menuId: 7,
            name: "Pav Bhaji",
            description: "Made with vegetables",
            price: "10"
        },
        {
            menuId: 10,
            name: "Samosa",
            description: "Made with potatoes",
            price: "6.50"
        }
    ];

    return(
        <View style={tw`flex-1 justify-center items-center bg-white`}>
            <ScrollView>
                {data.length == 0 ? <Text style={tw`text-black text-lg font-semibold text-center justify-center content-center my-70`}>Your card is empty</Text>:
                data.map((item) => <CardItem key={item.menuId} cardData={item}></CardItem>)
                }
            </ScrollView> 
        </View>
    );
};

export default Cart;