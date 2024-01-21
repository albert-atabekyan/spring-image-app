import React from "react";
import "../css/imageList.css";
import ImageItem from "./ImageItem";
import {useEffect, useState} from "react";

const ImageList = ({images_id, setImages}) => {
    const [childCount, setChildCount] = useState(0);

    useEffect(() => {
        const parent = document.getElementsByClassName("imageList")[0];
    
        console.log(parent)
        function updateChildCount() {
            setChildCount(parent.children.length);
        }

        const observer = new MutationObserver(updateChildCount);
      
        const config = { childList: true };

        observer.observe(parent, config);
          
        updateChildCount();
      
        return () => {
            observer.disconnect();
        };
    }, []);

    if(childCount > 3) {
        const imageListElement = document.getElementsByClassName("imageList")[0];
        imageListElement.classList.add("imageListGrid")
        imageListElement.classList.remove("imageListFlex")
    } else {
        const imageListElement = document.getElementsByClassName("imageList")[0];
        imageListElement?.classList.remove("imageListGrid")
        imageListElement?.classList.add("imageListFlex")
    }

    const listItems = images_id.map((image_id) => {
            return <ImageItem image_id = {image_id}
                       setImages = {setImages}
                       key = {image_id}
                       >
            </ImageItem>
        }
    );

    return (
        <div className="imageList">
            {listItems}
        </div>
    );
};

export default ImageList;
