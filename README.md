# android-case-study

Displays the list of deals from "http://target-deals.herokuapp.com/api/deals".

When a deal is clicked it opens up the new Screen to show the the full details.

## Note
Picasso is used to load the image and it uses caching machanism to load the image from second time onwards.

Response from the deals API has a same Image URL (eg: http://lorempixel.com/400/400/) which returns a new image everytime. But Picassa's caching results in same image seen across whole list.