CREATE ALIAS IF NOT EXISTS uuidv7 FOR "com.mentorship.food_delivery_app.config.H2Functions.uuidv7";

CREATE TABLE IF NOT EXISTS system_config
(
    system_key   VARCHAR(50),
    system_value VARCHAR(50),
    system_type  VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS permission
(
    permission_id SERIAL PRIMARY KEY,
    permission    VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS role
(
    role_id   SERIAL PRIMARY KEY,
    role_name VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS role_permission
(
    permission_id INT NOT NULL,
    role_id       INT NOT NULL
);

CREATE TABLE IF NOT EXISTS user_type
(
    user_type_id   SERIAL PRIMARY KEY,
    user_type_name VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS users
(
    user_id         UUID             DEFAULT uuidv7() PRIMARY KEY,
    user_type_id    INT              NOT NULL,
    user_first_name VARCHAR(50)      NOT NULL,
    user_last_name  VARCHAR(50)      NOT NULL,
    user_birth_date DATE,
    user_phone      VARCHAR(15)      NOT NULL,
    user_email      VARCHAR(50)      NOT NULL UNIQUE,
    user_password   VARCHAR(255)     NOT NULL,
    joined_at       TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    last_login      TIMESTAMP,
    is_enabled      BOOLEAN          DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS user_role
(
    role_id INT  NOT NULL,
    user_id UUID NOT NULL
);

CREATE TABLE IF NOT EXISTS customer
(
    customer_id                   UUID DEFAULT uuidv7() PRIMARY KEY,
    customer_user_id              UUID NOT NULL UNIQUE,
    customer_default_address_id   UUID,
    customer_preferred_payment_id INT
);

CREATE TABLE IF NOT EXISTS customer_address
(
    customer_address_id           UUID DEFAULT uuidv7() PRIMARY KEY,
    customer_address_customer_id  UUID         NOT NULL,
    customer_address_label        VARCHAR(20)  NOT NULL,
    customer_address_city         VARCHAR(20)  NOT NULL,
    customer_address_street       VARCHAR(20)  NOT NULL,
    customer_address_building     VARCHAR(20)  NOT NULL,
    customer_address_apartment    VARCHAR(20)  NOT NULL,
    customer_address_phone_number VARCHAR(15)  NOT NULL,
    customer_address_note         VARCHAR(500)
);

CREATE TABLE IF NOT EXISTS restaurant
(
    restaurant_id          UUID DEFAULT uuidv7() PRIMARY KEY,
    restaurant_name        VARCHAR(100) NOT NULL,
    restaurant_description VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS restaurant_branch
(
    branch_id                      UUID DEFAULT uuidv7() PRIMARY KEY,
    branch_rest_id                 UUID          NOT NULL,
    branch_delivery_fee            DECIMAL(6, 2) CHECK (branch_delivery_fee >= 0),
    branch_min_order               DECIMAL(6, 2) CHECK (branch_min_order >= 0),
    branch_city                    VARCHAR(20)   NOT NULL,
    branch_open_time               TIME          NOT NULL,
    branch_close_time              TIME          NOT NULL,
    branch_phone_number            VARCHAR(15)   NOT NULL,
    branch_estimated_delivery_time INT,
    created_at                     TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    last_modified                  TIMESTAMP,
    created_by                     UUID          NOT NULL,
    modified_by                    UUID,
    admin_id                       UUID
);

CREATE TABLE IF NOT EXISTS category
(
    category_id   SERIAL PRIMARY KEY,
    category_name VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS restaurant_category
(
    category_id   INT  NOT NULL,
    restaurant_id UUID NOT NULL,
    PRIMARY KEY (category_id, restaurant_id)
);

CREATE TABLE IF NOT EXISTS restaurant_menu
(
    restaurant_menu_id      UUID DEFAULT uuidv7() PRIMARY KEY,
    restaurant_menu_rest_id UUID        NOT NULL,
    restaurant_menu_name    VARCHAR(30) NOT NULL,
    created_at              TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    last_modified           TIMESTAMP,
    created_by              UUID        NOT NULL,
    modified_by             UUID
);

CREATE TABLE IF NOT EXISTS menu_item
(
    menu_item_id          UUID DEFAULT uuidv7() PRIMARY KEY,
    restaurant_menu_id    UUID          NOT NULL,
    menu_item_description VARCHAR(255),
    menu_item_name        VARCHAR(50)   NOT NULL,
    menu_item_price       DECIMAL(9, 2) NOT NULL CHECK (menu_item_price > 0),
    created_at            TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    last_modified         TIMESTAMP,
    created_by            UUID          NOT NULL,
    modified_by           UUID
);

CREATE TABLE IF NOT EXISTS restaurant_rate
(
    restaurant_rate_id            UUID DEFAULT uuidv7() PRIMARY KEY,
    restaurant_rate_restaurant_id UUID         NOT NULL,
    restaurant_rate_customer_id   UUID         NOT NULL,
    restaurant_rate_rating        INT,
    restaurant_rate_comment       VARCHAR(500) NOT NULL,
    restaurant_rate_created_at    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS coupon
(
    coupon_id             UUID DEFAULT uuidv7() PRIMARY KEY,
    coupon_restaurant_id  UUID          NOT NULL,
    coupon_amount         DECIMAL(6, 2) CHECK (coupon_amount > 0),
    coupon_available_from TIMESTAMP     NOT NULL,
    coupon_available_to   TIMESTAMP     NOT NULL,
    coupon_is_active      BOOLEAN       NOT NULL,
    coupon_created_at     TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    coupon_last_modified  TIMESTAMP
);

CREATE TABLE IF NOT EXISTS cart
(
    cart_id              UUID DEFAULT uuidv7() PRIMARY KEY,
    cart_customer_id     UUID    NOT NULL,
    is_locked CHAR(1) DEFAULT '0',
    cart_current_rest_id UUID
);

CREATE TABLE IF NOT EXISTS cart_item
(
    cart_item_cart_id  UUID,
    menu_item_id       UUID,
    cart_item_quantity INT CHECK (cart_item_quantity > 0),
    cart_item_note     VARCHAR(255),
    PRIMARY KEY (cart_item_cart_id, menu_item_id)
);

CREATE TABLE IF NOT EXISTS order_status
(
    status VARCHAR(20) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS order_tracking
(
    order_tracking_id          UUID DEFAULT uuidv7() PRIMARY KEY,
    order_tracking_status      VARCHAR(20),
    order_tracking_order_id    UUID        NOT NULL,
    order_tracking_description VARCHAR(50) NOT NULL,
    order_tracking_created_at  TIMESTAMP   DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS orders
(
    order_id                   UUID DEFAULT uuidv7() PRIMARY KEY,
    order_address_id           UUID           NOT NULL,
    order_customer_id          UUID           NOT NULL,
    order_restaurant_branch_id UUID           NOT NULL,
    order_coupon_id            UUID,
    order_subtotal             DECIMAL(7, 2)  CHECK (order_subtotal > 0),
    order_fee                  DECIMAL(6, 2)  DEFAULT 0,
    order_total                DECIMAL(10, 2) NOT NULL,
    order_date                 TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
    order_note                 VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS order_item
(
    order_item_id           UUID DEFAULT uuidv7() PRIMARY KEY,
    order_item_order_id     UUID          NOT NULL,
    order_item_menu_item_id UUID          NOT NULL,
    order_item_unit_price   DECIMAL(9, 2) NOT NULL CHECK (order_item_unit_price > 0),
    order_item_quantity     INT           NOT NULL CHECK (order_item_quantity > 0),
    order_item_subtotal     DECIMAL(10, 2),
    order_item_note         VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS payment_integration_type
(
    payment_integration_type_name VARCHAR(20) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS payment_type_config
(
    payment_type_config_id   SERIAL PRIMARY KEY,
    payment_integration_type VARCHAR(20) NOT NULL,
    config_details           TEXT        NOT NULL
);

CREATE TABLE IF NOT EXISTS transaction_status
(
    status VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS transaction
(
    transaction_id             UUID DEFAULT uuidv7() PRIMARY KEY,
    transaction_status         VARCHAR(20)    NOT NULL,
    transaction_order_id       UUID           NOT NULL,
    transaction_payment_type   VARCHAR(20),
    transaction_customer_id    UUID           NOT NULL,
    transaction_rest_branch_id UUID           NOT NULL,
    transaction_amount         DECIMAL(10, 2) CHECK (transaction_amount > 0),
    transaction_time           TIMESTAMP      DEFAULT CURRENT_TIMESTAMP
);