import React from 'react';
import { Avatar, Card, CardContent, List, ListItem, Typography } from '@mui/material';
import BeachAccessIcon from '@mui/icons-material/BeachAccess';


interface Activity {
    id: number;
    title: string;
    price: number;
    rating: number;
    specialOffer: boolean;
    supplierName: string;
    supplierLocation: string;
}

interface ActivityListProps {
    activities: Activity[];
}

const ActivityList:React.FC<ActivityListProps> = ({ activities }) => {
    return (
        <List>
            {activities.map((activity, index) => (
                <ListItem key={activity.id}>
                    <Avatar>
                        <BeachAccessIcon />
                    </Avatar>
                    <Card key={index} sx={{width: 400, margin: 2 }}>
                        <CardContent>
                            <Typography variant="body2" color="text.primary">
                                {activity.title}
                            </Typography>
                            <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
                                Price : {activity.price}
                            </Typography>
                            <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
                                Rating : {activity.rating}
                            </Typography>
                            <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
                                Supplier : {activity.supplierName}
                                <div style={{marginLeft: '5px'}}>
                                    {activity.supplierLocation}
                                </div>
                            </Typography>
                                {activity.specialOffer && <span>Special Offer!</span>}
                        </CardContent>
                        </Card>
                </ListItem>
            ))}
        </List>
    );
};

export default ActivityList;