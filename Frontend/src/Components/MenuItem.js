import * as React from 'react';
import { useState } from 'react';
import { StyleSheet, View, Pressable, TouchableOpacity } from 'react-native';
import { Avatar, Button, Card, Checkbox, Text, TextInput } from 'react-native-paper';
import tw from "twrnc";
import SampleImg from '../../assets/images/DalBites.png';
import * as ImagePicker from 'expo-image-picker';

const LeftContent = props => <Avatar.Icon {...props} icon="folder" />

const MenuItem = ({cardData, onUpdateCard, onDeleteCard}) => {

    const [name, setName] = useState(cardData.name || '');
    const [description, setDescription] = useState(cardData.description || '');
    const [prepTime, setPrepTime] = useState((cardData.time ? cardData.time.toString() : '') || '');
    const [cost, setCost] = useState((cardData.price ? cardData.price.toString() : '') || '');
    const [isAvailable, setIsAvailable] = useState(cardData.is_available || false);
    const [file, setFile] = useState(cardData.menu_image || SampleImg);
    const [fileObj, setFileObj] = useState("");
    const isNew = cardData.isNew;

    // const handleImageChange = async () => {
    //     try {
    //         const options = {
    //             noData: true,
    //         };
    //         ImagePicker.launchImageLibrary(options, async (response) => {
    //             console.log(response);
    //         });
    //     } catch (error) {
    //         console.log(error);
    //     }
    // };

    const createFormData = (uri) => {
        const fileName = uri.split('/').pop();
        const fileType = fileName.split('.').pop();
        const formData = new FormData();
        formData.append('file', {
          name: fileName,
          uri,
          type: `image/${fileType}`,
        });
        setFile(uri);
        setFileObj({
            name: fileName,
            uri,
            type: `image/${fileType}`,
        });
        console.log(fileObj);
        return formData;
    }

    const handleImageChange = async () => {
        let result = await ImagePicker.launchImageLibraryAsync({
          allowsEditing: true,
          mediaTypes: ImagePicker.MediaTypeOptions.Images,
          quality: 1,
          base64: true,
        });
    
        if (!result.canceled) {
            console.log("========");
            const imgUri = result.assets[0].uri;
            const tmp = createFormData(imgUri);
            console.log(tmp._parts[0][1]);
            // console.log(Object.keys(result.assets[0]));
            // console.log(result.assets[0].base64);
        } else {
          alert('You did not select any image.');
        }
      };

    const handleUpdateCard = () => {
        onUpdateCard({ id: cardData.id, name, description, prepTime, cost, isAvailable, isNew, fileObj});
    };

    const handleDeleteCard = () => {
        onDeleteCard({menuId: cardData.id, isNew});
    };

    return(
    <View style={tw`flex-1 justify-center items-center bg-white py-2`}>
        <Card style={{borderColor: 'transparent', backgroundColor: 'white', borderBottomColor: 'white', width: '98%', alignSelf: "center", paddingBottom: '10px'}}>
            <Card.Content>
            {/* <Card.Cover source={{ uri: 'https://picsum.photos/700' }} style={{width: '100%', alignSelf: "center"}}/> */}
            <Card.Cover source={file} style={{width: '100%', alignSelf: "center"}}/>
                <View style={styles.container}>
                    <TouchableOpacity style={tw`bg-yellow-500 rounded-lg py-2 px-4`} onPress={handleImageChange}>
                        <Text style={tw`text-black font-semibold`}>Choose Image</Text>
                    </TouchableOpacity>
                </View>
                <TextInput theme={{colors: {primary: 'black'}}} variant="titleSmall" style={tw`bg-white py-0 px-0 w-80 mb-4 h-10 mt-2`} placeholder="Name" value={name} onChangeText={setName}></TextInput>
                <TextInput theme={{colors: {primary: 'black'}}} variant="titleSmall" style={tw`bg-white py-0 px-0 w-80 mb-4 h-10 mt-2`} placeholder="Description" value={description} onChangeText={setDescription}></TextInput>
                <TextInput theme={{colors: {primary: 'black'}}} variant="titleSmall" style={tw`bg-white py-0 px-0 w-80 mb-4 h-10 mt-2`} placeholder="Prep Time" value={prepTime} onChangeText={setPrepTime}></TextInput>
                <TextInput theme={{colors: {primary: 'black'}}} variant="titleSmall" style={tw`bg-white py-0 px-0 w-80 mb-4 h-10 mt-2`} placeholder="Cost" value={cost} onChangeText={setCost}></TextInput>
                <View style={styles.checkboxContainer}>
                    <Text variant="titleSmall" style={styles.label}>Available: </Text>
                    <Checkbox color='#FFEB3B' style={styles.checkbox} status={isAvailable ? 'checked' : 'unchecked'} onPress={() => {setIsAvailable(!isAvailable)}}/>
                </View>
            </Card.Content>
            <Card.Actions>
                <TouchableOpacity style={tw`bg-yellow-500 rounded-lg py-2 px-4`} onPress={handleUpdateCard}>
                    <Text style={tw`text-black text-lg font-semibold`}>Save</Text>
                </TouchableOpacity>
                <TouchableOpacity style={tw`bg-yellow-500 rounded-lg py-2 px-4`} onPress={handleDeleteCard}>
                    <Text style={tw`text-black text-lg font-semibold`}>Delete</Text>
                </TouchableOpacity>
            </Card.Actions>
        </Card>
    </View>
    );
};


const styles = StyleSheet.create({
    input: {
        height: 40,
        width: 250,
        margin: 12,
        borderRadius: 0,  
        backgroundColor: 'white',
        borderBottomWidth: 1 , 
        textAlignVertical:'bottom', 
        paddingTop: 0, 
        paddingBottom:0
    },
    container: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
        marginTop: 30
    },
    checkboxContainer: {
        flexDirection: 'row',
        marginBottom: 20,
    },
    checkbox: {
        alignSelf: 'center',
    },
    label: {
        margin: 8,
    }
});
  
export default MenuItem;


// import React, { useState } from 'react';
// import { Pressable, Text, TextInput, View } from 'react-native';
// import tw from "twrnc";

// const Card = ({ cardData, onUpdateCard, onDeleteCard }) => {
//     const [name, setName] = useState(cardData.name || '');
//     const [description, setDescription] = useState(cardData.description || '');

//   const handleNameChange = (e) => {
//     setName(e.target.value);
//   };

//   const handleDescriptionChange = (e) => {
//     setDescription(e.target.value);
//   };

//   const handleUpdateCard = () => {
//     console.log(name);
//     onUpdateCard({ id: cardData.id, name, description });
//   };

//   const handleDeleteCard = () => {
//     onDeleteCard(cardData.id);
//   };

//   return (
//     <View className="card">
//       <TextInput type="text" value={name} onChangeText={setName}></TextInput>
//       <TextInput value={description} onChangeText={setDescription}></TextInput>
//       <Pressable style={tw`bg-yellow-500 rounded-lg py-2 px-4`} onPress={handleDeleteCard}>
//                     <Text style={tw`text-black text-lg font-semibold`}>Delete</Text>
//                 </Pressable>
//       <Pressable style={tw`bg-yellow-500 rounded-lg py-2 px-4`} onPress={handleUpdateCard}>
//                     <Text style={tw`text-black text-lg font-semibold`}>Save</Text>
//                 </Pressable>
//     </View>
//   );
// };

// export default Card;
