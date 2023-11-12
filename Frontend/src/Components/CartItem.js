import * as React from 'react';
import { useState } from 'react';
import { Button, Dimensions, Image, StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import tw from "twrnc";

const CartItem = ({cardData}) => {
  const [quantity, setQuantity] = useState(cardData.quantity || 1);
    
    const increaseQuantity = () => {
        setQuantity(quantity + 1);
    };
    
    const decreaseQuantity = () => {
        if (quantity > 1) {
            setQuantity(quantity - 1);
        }
    };
    
    const removeFromCart = () => {
        //remove from cart logic here
    };

    return (
        <View style={styles.card}>
            <Text style={styles.title}>{cardData.name}</Text>
            <Text style={styles.price}>Price: ${cardData.price}</Text>
            <Text style={styles.price}>Total: ${cardData.price * quantity}</Text>
            <View style={styles.quantityContainer}>
                <TouchableOpacity style={tw`bg-yellow-500 rounded-lg py-2 px-4`} onPress={decreaseQuantity}>
                    <Text style={tw`text-black text-lg font-semibold`}>-</Text>
                </TouchableOpacity>
                <Text style={styles.quantity}>{quantity}</Text>
                <TouchableOpacity style={tw`bg-yellow-500 rounded-lg py-2 px-4`} onPress={increaseQuantity}>
                    <Text style={tw`text-black text-lg font-semibold`}>+</Text>
                </TouchableOpacity>
            </View>
            <TouchableOpacity style={tw`bg-yellow-500 py-2 px-4`} onPress={removeFromCart}>
                <Text style={tw`text-black text-lg font-semibold text-center justify-center content-center`}>Remove from Cart</Text>
            </TouchableOpacity>
        </View>
    );
};

const screenWidth = Dimensions.get('window').width;


const styles = StyleSheet.create({
    card: {
      marginTop: 3,  
      width: screenWidth*0.95, // Make the card width 100% of the screen
      backgroundColor: '#fff',
      borderRadius: 8,
      borderColor: 'black',
      marginBottom: 16,
      elevation: 3,
      overflow: 'hidden', // Clip child Image component to border-radius
    },
    coverImage: {
      height: 200, // Adjust the height as needed
      width: '100%',
      resizeMode: 'cover',
    },
    title: {
      fontSize: 20,
      fontWeight: 'bold',
      marginBottom: 8,
      paddingHorizontal: 16,
      paddingTop: 8,
    },
    description: {
      fontSize: 16,
      marginBottom: 8,
      paddingHorizontal: 16,
    },
    price: {
      fontSize: 18,
      fontWeight: 'bold',
      marginBottom: 8,
      paddingHorizontal: 16,
    },
    quantityContainer: {
      flexDirection: 'row',
      alignItems: 'center',
      marginBottom: 8,
      paddingHorizontal: 16,
    },
    quantity: {
      fontSize: 18,
      marginHorizontal: 8,
    },
  });

export default CartItem;