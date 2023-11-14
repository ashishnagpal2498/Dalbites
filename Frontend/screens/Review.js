import React, { useState } from "react";
import { View, Text,TouchableOpacity, StyleSheet,TextInput } from 'react-native';
import { ScrollView } from 'react-native-gesture-handler';
import moment from 'moment';

const Review = ({ route }) => {
    const { orderId } = route.params;
  

    const handleSaveDescriptionButton = ()=>{

    };
    const handleChangeReviewState = ()=>{

    };

    var date = moment()
      .utcOffset('-04:00')
      .format('DD-MM-YYYY hh:mm a');


    // const orderHistory =
    //     {
    //       orderId: '00001',
    //       name: '09-11-2023',
    //     };

    // const [ratings, setRatings] = useState(Array(orderHistory.length).fill(0));

    // const rateOrder = (orderIndex, rating) => {
    //   const newRatings = [...ratings];
    //   newRatings[orderIndex] = rating;
    //   setRatings(newRatings);
    // };

    return(
        <ScrollView style={styles.reviewScreenContainer}>

            <View style={styles.reviewInfoContainer}>
                <Text style={styles.infoText}>{orderId}</Text>
                <Text style={styles.infoText}>{date}</Text>
            </View>

            <View style={styles.reviewContainer}>

                <View style={styles.ratingBox}>

                    {[1, 2, 3, 4, 5].map((star, i) => (
                        <TouchableOpacity key={i} onPress={() => rateOrder(index, star)}>
                            <Text style={[styles.stars
                            // { color: ratings[index] >= star ? '#EAB308' : 'gray' }
                            ]}> ★ </Text>
                        </TouchableOpacity>
                    ))}

                </View>

                <View style={styles.descriptionBox}>
                    <TextInput
                        style={styles.descriptiontext}
                        multiline={true}
                        numberOfLines={4}
                        placeholder="Add a Review"
                        onChangeText={(text) => handleChangeReviewState("Add a Review", text)}
                    />
                </View>

                <View style={styles.saveButtonContainer}>
                    <TouchableOpacity style={styles.saveButton} onPress={handleSaveDescriptionButton}>
                        <Text style={styles.buttonText}>Save Review</Text>
                    </TouchableOpacity>                
                </View>

            </View>

        </ScrollView>
    );
};

const styles = StyleSheet.create({
    reviewScreenContainer:{
        flex: 1,
        flexDirection: "column",
        backgroundColor: "white",
    },
    reviewInfoContainer:{
        margin:5,
        marginHorizontal:10,
        flexDirection:"row",
        justifyContent:"space-between",
        backgroundColor: "black",
    },
    infoText:{
        fontSize: 16,
        color:"white",
        padding:3,
        paddingHorizontal:7,
    },
    reviewContainer:{
        borderWidth:1,
        marginHorizontal:10,
        padding:10,
        paddingTop:2,
    },
    ratingBox:{
        flexDirection: "row",
        alignSelf: 'center',
        alignSelf:"center",
        margin:5,
    },
    stars:{
        fontSize:25,
    },
    saveButtonContainer:{
        marginTop:7,
        width:"90%",
        alignSelf:"center",
        alignItems:"center",
    },
    saveButton:{
        backgroundColor: "#EAB308",
        paddingVertical: 7,
        paddingHorizontal: 15,
        borderRadius: 10,
    },
    buttonText:{
        fontSize: 20,
        fontWeight: "bold",
        textAlign: "center",
    },
    descriptionBox:{
        width:"100%",
        borderWidth:1,
        alignSelf:"center",
        flexDirection:"column",       
    },
    descriptiontext:{
        padding:3,
        fontSize: 14,
        textAlignVertical:"top",
    },

});

export default Review;