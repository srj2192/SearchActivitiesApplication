import React, { useState, useEffect } from 'react';
import axios from 'axios';
import SearchBar from '../components/SearchBar';
import ActivityList from '../components/ActivityList';

const HomePage = () => {
    const [activities, setActivities] = useState([]);
    const [query, setQuery] = useState("");

    useEffect(() => {
        const fetchActivities = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/activities?title=${query}`);
                setActivities(response.data);
            } catch (error) {
                console.error("There was an error fetching the activities!", error);
            }
        };
        fetchActivities();
    }, [query]);

    return (
        <div>
            <SearchBar onSearch={setQuery} />
            <ActivityList activities={activities} />
        </div>
    );
};

export default HomePage;
