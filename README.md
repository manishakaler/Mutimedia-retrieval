# Floor Plan Retrieval

An Android application for retrieving similar floor plans and architectural images using graph-based structural matching and semantic ranking.

## Overview

This project helps architects and architecture students search for similar floor plans from a digital repository of architectural layouts. The system allows users to upload images to a cloud-hosted Firebase database and retrieve the most similar floor plans for a given query image. The retrieval pipeline performs feature extraction, matching, and ranking on the server side to return relevant results.

## Features

- Reverse image search for floor plans
- Upload floor plan images to a Firebase database
- View uploaded images inside the application
- Retrieve similar layouts for a given query image
- Rank results using structural similarity and semantic difference

## Tech Stack

- **Android/Kotlin** for the mobile application
- Firebase Realtime Database and Firebase Storage for cloud-hosted image storage
- Python for server-side retrieval and matching
- Android Studio for development

## Dataset

The project uses the SESYD floor plan dataset. The database contains 5 layout types, with 20 floor plan images per type, and includes 12 standard decor symbols.

## Retrieval Pipeline

The retrieval process is divided into three main stages:

1. Feature extraction  
   - Layout segmentation
   - Boundary extraction and morphological operations
   - Connected component analysis for room segmentation
   - Adjacent Room Detection (ARD)
   - Graph construction from room adjacency

2. Structural matching  
   - Graph spectral embedding is used to represent each layout in a pattern space
   - Similarity is computed by measuring distances between embedded graph representations

3. Semantic ranking  
   - Retrieved floor plans are re-ranked based on semantic differences, such as the arrangement and number of furniture objects inside rooms

## App Workflow

The Android app includes the following screens:

- Boot activity
- Home screen
- Search screen
- Upload pictures screen
- Show uploads screen

Users can select a query image, upload images to the database, and view ranked retrieval results directly in the app.

## Results

The application successfully implements:
- Reverse image search
- Image upload to the database
- Display of uploaded images
- Retrieval of the top similar floor plans for a query image

## Use Cases

This project can be useful for:
- Architects searching for similar existing layouts during design
- Architecture students storing and retrieving project layouts efficiently
- Exploring content-based retrieval techniques for architectural floor plans
