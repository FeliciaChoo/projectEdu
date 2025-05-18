
INSERT INTO project (
    title,
    status,
    description,
    category,
    survey_link,
    goal_amount,
    current_amount,
    end_date,
    bank_name,
    account_no,
    account_holder_name,
    image_url
) VALUES
      (
          'Smart Hydroponics System', 'Active',
          'IoT-based system for vertical farming','Agriculture', 'http://bit.ly/survey001',
          5000.00, 1200.00, '2025-07-15', 'CIMB',
          '1234-56-789', 'Aina Khalid',
          '/images/p1img.jpg'
      ),

      (
          'Organic Fertiliser Campaign', 'Completed',
          'Promoting eco-friendly fertilisers', 'Technology','http://bit.ly/survey002',
          3000.00, 3000.00, '2025-04-10', 'MAYBANK',
          '0987-45-123', 'Wong Kai Qi',
          '/images/p2img.jpg'
      ),

      (
          'Urban Farming Education App', 'Active',
          'Educational app for urban agriculture', 'Environment','http://bit.ly/survey003',
          7000.00, 4200.00, '2025-08-20', 'OCBC',
          '4466-23-466', 'Nurul Syafiqah',
          '/images/p3img.png'
      );

