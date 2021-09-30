package com.estafet.learning.sprint7;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Globals {

    // Note that we should avoid using such initialization using Streams, as it could cause a huge performance overhead and lots of garbage objects are created just to initialize the map.
    public static Map<String, Double> ARTICLES_DEFAULT_MAP_MUTABLE = Stream.of(new Object[][] {
            { "NZXT H510", 74.99 },
            { "AMD Ryzen 5 5700G", 369.99 },
            { "Asus ROG Strix B550-E Gaming", 209.07 },
            { "Nvidia GeForce RTX 3060 Ti", 2899.99 },
            { "Corsair Vengeance LPX 16GB (2x8GB) DDR4-3200", 77.99 },
            { "Addlink S70 512GB NVMe SSD", 84.99 },
            { "WD Black 1TB", 69.99 },
            { "Corsair TX650M 650W", 129.99 },
    }).collect(Collectors.toMap(data -> (String) data[0], data -> (Double) data[1]));

    public static final String[] COUNTRIES = new String[]{"Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegowina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", "Congo, the Democratic Republic of the", "Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia (Hrvatska)", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland", "France", "France Metropolitan", "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and Mc Donald Islands", "Holy See (Vatican City State)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran (Islamic Republic of)", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea, Democratic People's Republic of", "Korea, Republic of", "Kuwait", "Kyrgyzstan", "Lao, People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia, The Former Yugoslav Republic of", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia, Federated States of", "Moldova, Republic of", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone", "Singapore", "Slovakia (Slovak Republic)", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "Spain", "Sri Lanka", "St. Helena", "St. Pierre and Miquelon", "Sudan", "Suriname", "Svalbard and Jan Mayen Islands", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic", "Taiwan, Province of China", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Virgin Islands (British)", "Virgin Islands (U.S.)", "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Yugoslavia", "Zambia", "Zimbabwe", "Palestine"};
//    public static Map<String, Double> articlesDefaultMap = new LinkedHashMap<String, Double>();
/*    public static final Map<String, Double> ARTICLES_DEFAULT_MAP_MUTABLE_JAVA9 = Map.of("NZXT H510", 74.99, "AMD Ryzen 5 5700G", 369.99, "Asus ROG Strix B550-E Gaming",
            209.07, "Nvidia GeForce RTX 3060 Ti", 2899.99, "Corsair Vengeance LPX 16GB (2x8GB) DDR4-3200", 77.99,
            "Addlink S70 512GB NVMe SSD", 84.99, "WD Black 1TB", 69.99, "Corsair TX650M 650W", 129.99);*/

    public static final Map<String, Double> ARTICLES_DEFAULT_MAP = new LinkedHashMap<>(ARTICLES_DEFAULT_MAP_MUTABLE);
    public static final String[] STUDENTNAMES = new String[]{"Faiz Hoffman", "Mahira Byers", "Oskar Mack", "Fraya Branch", "Marta Nash", "Dollie Whitney", "Izabelle Bate", "Kendrick Robertson", "Jasper Savage", "Sadia Drew", "Felicity Mcfarlane", "Melody Martinez", "Keir Mcclure", "Onur Howell", "Georgina O'Doherty", "Regina Ahmad", "Kallum Brookes", "Lukasz Barr", "Ada Marsden", "Malik Thorpe", "Sienna-Rose Peck", "Ayaz Diaz", "Richie Hampton", "Humza Faulkner", "Lamar Cooley", "Murat Blaese", "Gage Wormald", "Jacqueline Cisneros", "Branden Maguire", "Hallie Dale", "Tyrique Mcculloch", "Blair Preece", "Carrie-Ann Coulson", "Amy Boone", "Marsha Sharples", "Ananya Tucker", "Reon Munoz", "Kodi Plummer", "Georgia Stout", "Mikayla Meyers", "Umer Frye", "Calista Kirk", "Vicki Ayers", "Saeed Haigh", "Mila Gross", "Shanai Laing", "Chaim Roach", "Jean-Luc Moss", "Charis Oneill", "Nela Colon", "Tahmina Odom", "Gabrielle Joyce", "Cheryl Conley", "Maaria Macias", "Emme Peel", "Brandan Velazquez", "Komal Landry", "Teo Hussain", "Darcie-Mae Gay", "Esmai Melia", "Shamas French", "Areeb Nicholls", "Safah Charles", "Karla Jones", "Osama Henderson", "Roman Reid", "Margaret Simon", "Nolan Walter", "Alex Rush", "Tara Neal", "Eira Schultz", "Ceara Kaufman", "Andreea Hodgson", "Hashir Chan", "Sanjay Horne", "Usamah Harper", "Luci Penn", "Elise Holt", "Iylah Harwood", "Akaash Stacey", "Amritpal Drummond", "Raj Harrington", "Bob Farrow", "Aurora Day", "Rylan Montes", "Eboni Mcfarland", "Brady Stark", "Conah Mac", "Zakary Reyna", "Pauline Lowry", "Arandeep Conway", "Rohit Hendricks", "Carlton Hooper", "Anisah Love", "Shanna Spencer", "Stefanie Whelan", "Ellenor Case", "Simona Browne", "Krzysztof Whitfield", "Alissa Carroll"};

    public static final String[] tablesToWorkWith = {"products", "customers", "orders"};
    public static final Map<String, ArrayList<String>> tablesToWorkWith2 = new LinkedHashMap<>();
    public static final String[][] tablesToWorkWith3 = {
            {"customers", "customer_number INT", "first_name VARCHAR(50)", "last_name VARCHAR(50)",
                    "address_line1 VARCHAR(50)", "address_line2 VARCHAR(50)", "year INT", "phone VARCHAR(50)", "city VARCHAR(50)", "postcode VARCHAR(50)"},
            {"products", "product_name VARCHAR(50)", "product_description VARCHAR(50)", "product_code VARCHAR(50)", "quantity INT", "price DOUBLE"},
            {"online_orders", "order_number INT", "customer_number INT", "product_code VARCHAR(50)", "quantity INT", "total_price DOUBLE", "date DATETIME"}
    };
    public static Connection connection = ConnectComponent.openConnection();

}
