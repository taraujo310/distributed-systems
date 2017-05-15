require 'drb'

server = DRbObject.new_with_uri('druby://localhost:61676')

puts server.say
