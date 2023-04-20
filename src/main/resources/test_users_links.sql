USE onskelistefilip;

INSERT INTO users (username, password)
VALUES
  ('user1', 'password1'),
  ('user2', 'password2');
  
INSERT INTO links (user_id, link)
VALUES
  ('1', 'https://www.matas.dk/mega-b-stress-250-kap'),
  ('1', 'https://www.matas.dk/solaray-cal-mag-citrat-med-vitamin-d-og-k3-240-kaps'),
  ('1', 'https://www.matas.dk/matas-striber-d-vitamin-70-mcg-180-tabl'),
  ('1', 'https://www.matas.dk/matas-striber-c-vitamin-depot-500-mg-200-tabl');
  