package com.chientran.application.model.mapper;

import com.chientran.application.entity.Comment;
import com.chientran.application.model.dto.CommentDTO;

public class CommentMapper {
    public static CommentDTO toCommentDTO(Comment comment){
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setContent(comment.getContent());

        return commentDTO;
    }

//    public static BrandDTO toBrandDTO(Brand brand){
//        BrandDTO brandDTO = new BrandDTO();
//        brandDTO.setId(brand.getId());
//        brandDTO.setName(brand.getName());
//        brandDTO.setDescription(brand.getDescription());
//        brandDTO.setThumbnail(brand.getThumbnail());
//        brandDTO.setStatus(brand.isStatus());
//
//        return brandDTO;
//    }
//
//    public static Brand toBrand(CreateBrandRequest createBrandRequest){
//        Brand brand= new Brand();
//        brand.setName(createBrandRequest.getName());
//        brand.setDescription(createBrandRequest.getDescription());
//        brand.setThumbnail(createBrandRequest.getThumbnail());
//        brand.setStatus(createBrandRequest.isStatus());
//        brand.setCreatedAt(new Timestamp(System.currentTimeMillis()));
//
//        return brand;
//    }
}
