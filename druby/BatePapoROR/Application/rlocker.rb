require_relative 'resource.rb'

class RLocker
  def initialize(filepath)
    @resource = initialize_resource(filepath)

    @readCount = 0
    @mutex = Mutex.new
    @writingMutex = Mutex.new
  end

  def access_read
    @mutex.synchronize do
      @readCount += 1
      @writingMutex.lock if @readCount == 1
    end

    yield(@resource)

    @mutex.synchronize do
      @readCount -= 1
      @writingMutex.unlock if @readCount == 0
    end
  end

  def access_write
    @writingMutex.synchronize do
      yield(@resource)
    end
  end

  private
  def initialize_resource(filepath="")
    raise NoNameForFileError, 'There is no file with empty name' unless filepath && !filepath.empty?
    Resource.new(filepath)
  end
end
