CREATE TABLE IF NOT EXISTS JOB_CONFIGURATION
(
    JOB_ID          INTEGER      NOT NULL,
    OBJECT_TYPE     VARCHAR(255) NOT NULL,
    DELIVERY_TYPE   VARCHAR(255),
    DELIVERY_STATUS VARCHAR(255),
    SHIPPING_POINT  VARCHAR(255),
    DAYS_BACK       INTEGER,
    REFERENCE_DATE  VARCHAR(50),
    SHIPMENT_STATUS VARCHAR(255),
    REASON_CODE     VARCHAR(255),
    CRON            VARCHAR(30),
    PRIMARY KEY (JOB_ID)
);

INSERT INTO JOB_CONFIGURATION
VALUES (1, 'DELIVERY', 'ZC01, ZNL2, ZI01, ZC05, ZC14', '10, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 20',
        'FRPA,FRPB,FRDB,FRDC,FRDI,FRDG,FRDL,FRDM,FRDF,FRDJ,FRDO', 5, 'DELIVERY_DATE', null, 'Technical closure', '*/10 * * * * *'),
       (2, 'DELIVERY', 'ZC01', '10, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 20',
        'PLDD, PLDB, PLDG, PLDA', 28, 'DELIVERY_DATE', null, 'Technical closure', '*/15 * * * * *'),
       (3, 'DELIVERY', 'ZOD', '10, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 20',
        'PLBA, PLBL, PLBD, PLBH, PLBO, PLBK, PLBB, PLBM, PLBC', 3, 'DELIVERY_DATE', null, 'Technical closure', '*/5 * * * * *'),
       (4, 'DELIVERY', 'ZC01, ZC05, ZC14, ZI01', '10, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 20',
        'PLBA, PLBL, PLBH, PLBO, PLBB, PLBK, PLBC, PLBM', 7, 'DELIVERY_DATE', null, 'Technical closure', '*/10 * * * * *'),
       (5, 'DELIVERY', 'ZC01, ZC05, ZC14, ZI01', '10, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 20',
        'PLBA, PLBL, PLBH, PLBO, PLBB, PLBK, PLBC, PLBM', 7, 'DELIVERY_DATE', null, 'Technical closure', '*/10 * * * * *'),
       (6, 'DELIVERY', 'ZC01, ZC05, ZC14, ZI01', '10, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 20',
        'PLBA, PLBL, PLBH, PLBO, PLBB, PLBK, PLBC, PLBM', 7, 'DELIVERY_DATE', null, 'Technical closure', '*/15 * * * * *'),
       (7, 'DELIVERY', 'ZC01, ZC14, NL', '10, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 20',
        'ES01, ES02, ES03, ES04, PT03, ESDC, ESFA, ESFB, ESDE', 21, 'DELIVERY_DATE', null, 'Technical closure', '*/10 * * * * *'),
       (8, 'DELIVERY', 'ZOX1', '10, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 20',
        'AL02, CO10, CO20, CO30, CO40, CO50, CO60, CO70, CO80, PW1, RLS, RN7, RW7', 7, 'DELIVERY_DATE', null, 'Technical closure', '*/25 * * * * *'),
       (9, 'DELIVERY', 'ZIN, ZLP, ZDI, ZLA', '10, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 20',
        'AL05, DLK, PW1, RLN, RLO, RLS, RM1, RN7, RS1, RS2, RS4, RSO, RW7', 5, 'DELIVERY_DATE', null, 'Technical closure', '*/20 * * * * *'),
       (10, 'DELIVERY', 'ZOX1', '10, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 20',
        'AL02, CO10, CO20, CO30, CO40, CO50, CO60, CO70, CO80, PW1, RLS, RN7, RW7', 70, 'DELIVERY_DATE', null, 'Technical closure', '*/10 * * * * *'),
       (11, 'DELIVERY', 'ZOX1', '10, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 20',
        'AL02, CO10, CO20, CO30, CO40, CO50, CO60, CO70, CO80, PW1, RLS, RN7, RW7', 21, 'DELIVERY_DATE', null, 'Technical closure', '*/10 * * * * *'),
       (12, 'SHIPMENT', null, null, null, null, null, '10, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 20', 'Technical closure', '0 * * ? * *');
