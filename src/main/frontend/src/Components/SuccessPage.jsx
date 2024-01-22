import "../css/upload.css";
import React, {useEffect, useState} from "react";
import ImageService from "../Service/ImageService";
import UploadFile from "./UploadFile";
import ImageList from "./ImageList";
import LogoutButton from "./LogoutButton";
import PopUp from "./PopUp";
import FormInput from "./FormInput";

const SuccessPage = () => {
    const [images, setImages] = useState([]);

    useEffect( () => {
        ImageService.getImages().then(res => setImages(res.data));
    }, []);

    return (
        <>
            <LogoutButton/>
            <UploadFile images_id={images} setImages={setImages}/>
            <ImageList images_id = {images} setImages={setImages}/>
        </>
    );
};

export default SuccessPage;