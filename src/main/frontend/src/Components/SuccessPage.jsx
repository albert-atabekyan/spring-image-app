import "../css/upload.css";
import React, {useEffect, useState} from "react";
import ImageService from "../Service/ImageService";
import UploadFile from "./UploadFile";
import ImageList from "./ImageList";
import LogoutButton from "./LogoutButton";

const SuccessPage = () => {
    const [images, setImages] = useState([]);

    useEffect( () => {
        ImageService.getImage().then(res => setImages(res.data));
    }, []);

    return (
        <>
            <LogoutButton/>
            <UploadFile images={images} setImages={setImages}/>
            <ImageList images = {images} setImages={setImages}/>
        </>
    );
};

export default SuccessPage;